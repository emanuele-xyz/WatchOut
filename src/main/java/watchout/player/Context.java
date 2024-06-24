package watchout.player;

import watchout.Pitch;
import watchout.common.Player;

import java.util.*;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;
import watchout.player.PlayerPeerServiceOuterClass.SeekerMessage;
import watchout.player.PlayerPeerServiceOuterClass.TokenMessage;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage;

public class Context {
    private static final double PLAYER_SPEED = 2.0; // meters per second

    private static Context instance;

    public synchronized static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private State state;
    private int id;
    private int port;
    private int pitchStartX;
    private int pitchStartY;
    private List<Player> otherPlayers;
    private Map<Integer, GRPCHandle> otherPlayersGRPCHandles;
    private int seekerId; // TODO: not needed
    private final Set<Integer> taggablePlayers;
    private int currentPitchX;
    private int currentPitchY;

    private Context() {
        taggablePlayers = new HashSet<>();
    }

    public synchronized void setState(State state) {
        this.state = state;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized int getPort() {
        return port;
    }

    public synchronized void setPort(int port) {
        this.port = port;
    }

    public synchronized void setPitchStart(int x, int y) {
        this.pitchStartX = x;
        this.pitchStartY = y;
    }

    public synchronized void setPlayers(List<Player> registeredPlayers) {
        this.otherPlayers = new ArrayList<>();
        registeredPlayers.stream().filter(p -> p.getId() != id).forEach(p -> this.otherPlayers.add(new Player(p)));
    }

    public synchronized void createGRPCHandles() {
        otherPlayersGRPCHandles = new HashMap<>();
        otherPlayers.forEach(p -> otherPlayersGRPCHandles.put(p.getId(), new GRPCHandle(p.getAddress(), p.getPort())));
    }

    public synchronized void greetAllPlayers() {
        GreetingRequest request = GreetingRequest.newBuilder()
                .setId(id)
                .setAddress("localhost")
                .setPort(port)
                .setPitchStartX(pitchStartX)
                .setPitchStartY(pitchStartY)
                .build();
        otherPlayers.forEach(p -> {
            GRPCHandle handle = otherPlayersGRPCHandles.get(p.getId());
            handle.getStub().greeting(request, new GRPCDefaultResponseObserver("Failed to send greeting to player " + p.getId()));
        });
    }

    public synchronized void onGreetingReceive(GreetingRequest greeting) {
        System.out.println("Greeting from player " + greeting.getId() + " - listening at " + greeting.getAddress() + ":" + greeting.getPort() + " - starting at (" + greeting.getPitchStartX() + "," + greeting.getPitchStartY() + ")");
        otherPlayers.add(new Player(greeting.getId(), greeting.getAddress(), greeting.getPort(), greeting.getPitchStartX(), greeting.getPitchStartY()));
        otherPlayersGRPCHandles.put(greeting.getId(), new GRPCHandle(greeting.getAddress(), greeting.getPort()));
        taggablePlayers.add(greeting.getId());
    }

    public synchronized void onGameStartReceive() {
        System.out.println("Game start notification received");

        switch (state) {
            case Idle: {
                if (mayBeSeeker()) {
                    System.out.println("I may be the seeker. Election started!");
                    state = State.Voted;
                    sendElectionToNextPlayer();
                }
            }
            break;

            case Voted:
            case Seeker:
            case Hider:
            case Safe:
            case Tagged: {
                // NOTE: game already started. Ignore the message.
            }
            break;
        }
    }

    public synchronized void onElectionReceive(ElectionMessage msg) {
        int candidateId = msg.getId();
        int candidatePitchStartX = msg.getPitchStartX();
        int candidatePitchStartY = msg.getPitchStartY();
        System.out.println("Received election with candidate player " + candidateId);

        switch (state) {
            case Idle: {
                state = State.Voted;
                if (isCloserToHomeBaseThan(candidateId, candidatePitchStartX, candidatePitchStartY)) {
                    sendElectionToNextPlayer();
                } else {
                    forwardElectionToNextPlayer(msg);
                }
            }
            break;

            case Voted: {
                if (id == candidateId) {
                    // NOTE: the election message went around the ring. We are the seeker!
                    System.out.println("I'm the seeker");
                    state = State.Seeker;
                    sendSeekerToNextPlayer();
                } else {
                    if (isCloserToHomeBaseThan(candidateId, candidatePitchStartX, candidatePitchStartY)) {
                        // NOTE: We have already voted and our candidate is better than the one we have received
                        System.out.println("Blocking election for player " + candidateId);
                    } else {
                        forwardElectionToNextPlayer(msg);
                    }
                }
            }
            break;

            case Seeker:
            case Hider:
            case Safe:
            case Tagged: {
                // NOTE: The ring has already elected a seeker. Thus, block any other election.
                System.out.println("Blocking election for player " + candidateId);
            }
            break;
        }
    }

    public synchronized void onSeekerReceive(SeekerMessage msg) {
        int seekerId = msg.getId();
        System.out.println("Player " + seekerId + " is the seeker");

        switch (state) {
            case Idle: {
                // NOTE: Being idle, the seeker message I'm receiving cannot be my own. Just forward it.
                // NOTE: Having missed the chance to vote, I'm a hider.
                state = State.Hider;
                this.seekerId = seekerId;
                System.out.println("I'm a hider");
                forwardSeekerToNextPlayer(msg);
            }
            break;

            case Voted: {
                // NOTE: The seeker message cannot be my own (only a seeker can send a seeker message). Just forward it.
                // NOTE: I voted and I'm not the seeker. Thus, I'm a hider.
                state = State.Hider;
                this.seekerId = seekerId;
                System.out.println("I'm a hider");
                forwardSeekerToNextPlayer(msg);
            }
            break;

            case Seeker: {
                if (id == seekerId) {
                    // NOTE: It's my own seeker message.
                    // NOTE: start token ring.
                    System.out.println("A new round has started");
                    // NOTE: return to starting position
                    currentPitchX = pitchStartX;
                    currentPitchY = pitchStartY;
                    new Thread(this::seekOtherPlayers).start();
                    sendTokenToNextPlayer();
                } else {
                    throw new IllegalStateException("Multiple seeker messages on the ring");
                }
            }
            break;

            case Hider:
            case Safe:
            case Tagged: {
                throw new IllegalStateException("Received seeker message while being " + state);
            }
            // break;
        }
    }

    public synchronized void onTokenReceive(TokenMessage msg) {
        System.out.println("Received token");

        switch (state) {
            case Idle: {
                // NOTE: We have skipped the election. I'm a hider.
                state = State.Hider;
                this.seekerId = msg.getSeekerId();
                System.out.println("I'm a hider");
                goForTheHomeBase();
                forwardTokenToNextPlayer(msg);
            }
            break;

            case Hider: {
                goForTheHomeBase();
                forwardTokenToNextPlayer(msg);
            }
            break;

            case Seeker: {
                if (!taggablePlayers.isEmpty()) {
                    forwardTokenToNextPlayer(msg);
                } else {
                    System.out.println("Token blocked, the round is over");
                    state = State.Idle;
                    sendRoundEndToAllPlayers();
                }
            }
            break;

            case Safe:
            case Tagged: {
                forwardTokenToNextPlayer(msg);
            }
            break;

            case Voted: {
                throw new IllegalStateException("Received token while being " + state);
            }
            // break;
        }
    }

    public synchronized void onTagReceive() {
        System.out.println("Tag received");
        switch (state) {
            case Idle: {
                System.out.println("I'm tagged");
                state = State.Tagged;
                sendRoundLeave();
            }
            break;

            case Hider: {
                // NOTE: A hider that is tagged may be trying to go for the home base.
                // NOTE: It still has to reach the home base, otherwise it would be safe.
                // NOTE: Thus, notify the possible waiting thread.
                System.out.println("I'm tagged");
                state = State.Tagged;
                sendRoundLeave();
                notify();
            }
            break;

            case Safe: {
                // NOTE: do nothing
            }
            break;

            case Voted:
            case Seeker:
            case Tagged: {
                throw new IllegalStateException("Received tag while being " + state);
            }
            //break;
        }
    }

    public synchronized void onLeaveRoundReceive(LeaveRoundMessage msg) {
        int id = msg.getId();
        boolean isTagged = msg.getIsTagged();
        System.out.println("Player " + id + " is " + (isTagged ? "tagged" : "safe"));
        taggablePlayers.remove(id);
    }

    public synchronized void onEndRoundReceive() {
        System.out.println("The round is over");
        switch (state) {
            case Idle:
            case Hider:
            case Safe:
            case Tagged: {
                state = State.Idle;
            }
            break;

            case Voted:
            case Seeker: {
                throw new IllegalStateException("Received round end while being " + state);
            }
            //break;
        }
    }

    private synchronized void seekOtherPlayers() {
        System.out.println("Pursuit started");

        taggablePlayers.clear();
        otherPlayers.forEach(p -> taggablePlayers.add(p.getId()));

        while (!taggablePlayers.isEmpty()) {
            // NOTE: pursue the closest taggable player.
            Player p = findClosestTaggablePlayer();
            double d = Pitch.getDistance(currentPitchX, currentPitchY, p.getPitchStartX(), p.getPitchStartY()) * Pitch.DISTANCE_TO_METERS_FACTOR;
            double timeToReachPlayer = d / PLAYER_SPEED;
            System.out.println("Pursuing player " + p.getId() + " reaching it in " + timeToReachPlayer + " seconds");
            try {
                wait((long) timeToReachPlayer * 1000 + 1); // NOTE: +1 to avoid 0 timeout due to 0 distance.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentPitchX = p.getPitchStartX();
            currentPitchY = p.getPitchStartY();

            // NOTE: after the pursuit, check if the player is still taggable.
            if (taggablePlayers.contains(p.getId())) {
                // NOTE: if it is, try to tag it
                sendTagToPlayer(p.getId());
                taggablePlayers.remove(p.getId());
            }
        }

        System.out.println("Pursuit ended");
    }

    private void goForTheHomeBase() {
        // NOTE: try to reach the home base
        {
            double distanceFromPitch = Pitch.getDistanceFromHomeBase(pitchStartX, pitchStartY) * Pitch.DISTANCE_TO_METERS_FACTOR;
            double timeToReachHomeBase = distanceFromPitch / PLAYER_SPEED;
            System.out.println("Going for the home base in " + timeToReachHomeBase + " seconds");
            try {
                wait((long) timeToReachHomeBase * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // NOTE: While going for the home base, we may either have been tagged or not
        switch (state) {
            case Hider: {
                // NOTE: We have reached the home base and we have not been tagged.
                state = State.Safe;
                System.out.println("I'm in the home base");
                try {
                    // NOTE: Player waits 10 seconds inside the home base.
                    wait(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendRoundLeave();
            }
            break;

            case Tagged: {
                // NOTE: we have been tagged while trying to reach the home base. We are out!
                // NOTE: we announce it when we actually get tagged.
                System.out.println("Failed to reach home base");
            }
            break;

            case Idle:
            case Voted:
            case Seeker:
            case Safe: {
                throw new IllegalStateException("Illegal state " + state + " after trying to reach the home base");
            } //break;
        }
    }

    private void sendElectionToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Sending election to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        ElectionMessage msg = ElectionMessage.newBuilder().setId(id).setPitchStartX(pitchStartX).setPitchStartY(pitchStartY).build();
        handle.getStub().election(msg, new GRPCDefaultResponseObserver("Failed to send election to player " + nextPlayerId));
    }

    private void forwardElectionToNextPlayer(ElectionMessage msg) {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Forwarding election to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().election(msg, new GRPCDefaultResponseObserver("Failed to forward election to player " + nextPlayerId));
    }

    private void sendSeekerToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Sending seeker announcement to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        SeekerMessage msg = SeekerMessage.newBuilder().setId(id).build();
        handle.getStub().seeker(msg, new GRPCDefaultResponseObserver("Failed to send seeker announcement to player " + nextPlayerId));
    }

    private void forwardSeekerToNextPlayer(SeekerMessage msg) {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Forwarding seeker to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().seeker(msg, new GRPCDefaultResponseObserver("Failed to forward seeker announcement to player " + nextPlayerId));
    }

    private void sendTokenToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Sending token to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        TokenMessage msg = TokenMessage.newBuilder().setSeekerId(id).build();
        handle.getStub().token(msg, new GRPCDefaultResponseObserver("Failed to send token to player " + nextPlayerId));
    }

    private void forwardTokenToNextPlayer(TokenMessage msg) {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Forwarding token to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().token(msg, new GRPCDefaultResponseObserver("Failed to forward token to player " + nextPlayerId));
    }

    private void sendTagToPlayer(int id) {
        System.out.println("Sending tag to player " + id);
        GRPCHandle handle = otherPlayersGRPCHandles.get(id);
        handle.getStub().tag(Empty.getDefaultInstance(), new GRPCDefaultResponseObserver("Failed to send tag to player " + id));
    }

    private void sendRoundLeave() {
        if (state != State.Tagged && state != State.Safe) {
            throw new IllegalStateException("Send round leave while being " + state);
        }
        LeaveRoundMessage msg = LeaveRoundMessage.newBuilder()
                .setId(id)
                .setIsTagged(state == State.Tagged)
                .build();
        otherPlayers.forEach(p -> {
            GRPCHandle handle = otherPlayersGRPCHandles.get(p.getId());
            handle.getStub().leaveRound(msg, new GRPCDefaultResponseObserver("Failed to signal round leave to player " + p.getId()));
        });
    }

    private void sendRoundEndToAllPlayers() {
        System.out.println("Announcing end of round to all other players");
        otherPlayers.forEach(p -> {
            GRPCHandle handle = otherPlayersGRPCHandles.get(p.getId());
            handle.getStub().endRound(Empty.getDefaultInstance(), new GRPCDefaultResponseObserver("Failed to send end of round to player " + p.getId()));
        });
    }

    private int findNextPlayerId() {
        // NOTE: we suppose there is at least another player
        int minId = otherPlayers.stream().mapToInt(Player::getId).min().getAsInt();
        int nextPlayerId = otherPlayers.stream().filter(p -> p.getId() > id).mapToInt(Player::getId).min().orElse(minId);
        return nextPlayerId;
    }

    private boolean mayBeSeeker() {
        return otherPlayers.stream().allMatch(this::isCloserToHomeBaseThan);
    }

    private boolean isCloserToHomeBaseThan(Player p) {
        return isCloserToHomeBaseThan(p.getId(), p.getPitchStartX(), p.getPitchStartY());
    }

    private boolean isCloserToHomeBaseThan(int otherId, int otherPitchStartX, int otherPitchStartY) {
        double myDistance = Pitch.getDistanceFromHomeBase(pitchStartX, pitchStartY);
        double otherDistance = Pitch.getDistanceFromHomeBase(otherPitchStartX, otherPitchStartY);
        if (myDistance != otherDistance) {
            return myDistance < otherDistance;
        } else {
            return id > otherId;
        }
    }

    private Player findClosestTaggablePlayer() {
        return otherPlayers.stream()
                .filter(p -> taggablePlayers.contains(p.getId()))
                .min((p1, p2) -> {
                    double d1 = Pitch.getDistance(currentPitchX, currentPitchY, p1.getPitchStartX(), p1.getPitchStartY());
                    double d2 = Pitch.getDistance(currentPitchX, currentPitchY, p2.getPitchStartX(), p2.getPitchStartY());
                    return Double.compare(d1, d2);
                })
                .orElse(null);
    }
}
