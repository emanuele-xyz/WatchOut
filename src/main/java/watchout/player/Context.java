package watchout.player;

import watchout.Pitch;
import watchout.common.Player;

import java.util.*;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;
import watchout.player.PlayerPeerServiceOuterClass.SeekerMessage;
import watchout.player.PlayerPeerServiceOuterClass.TokenMessage;

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
    private int seekerId; // TODO: may be removed
    private Set<Integer> taggablePlayers;

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
        GreetingRequest request = GreetingRequest.newBuilder().setId(id).setAddress("localhost").setPort(port).setPitchStartX(pitchStartX).setPitchStartY(pitchStartY).build();
        otherPlayers.forEach(p -> {
            GRPCHandle handle = otherPlayersGRPCHandles.get(p.getId());
            handle.getStub().greeting(request, new GRPCObserverGreetingResponse(p.getId(), p.getAddress(), p.getPort()));
        });
    }

    public synchronized void onGreetingReceive(GreetingRequest greeting) {
        System.out.println("Greeting from player " + greeting.getId() + " - listening at " + greeting.getAddress() + ":" + greeting.getPort() + " - starting at (" + greeting.getPitchStartX() + "," + greeting.getPitchStartY() + ")");
        otherPlayers.add(new Player(greeting.getId(), greeting.getAddress(), greeting.getPort(), greeting.getPitchStartX(), greeting.getPitchStartY()));
        otherPlayersGRPCHandles.put(greeting.getId(), new GRPCHandle(greeting.getAddress(), greeting.getPort()));
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
                    System.out.println("Game officially started!");
                    if (!taggablePlayers.isEmpty()) throw new IllegalStateException("The set of taggable players should be empty before starting the game");
                    otherPlayers.forEach(p -> taggablePlayers.add(p.getId()));
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
            case Seeker:
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

    private synchronized void seekOtherPlayers() {
        // NOTE: find the closest hider (neither safe nor tagged).
        Player player = otherPlayers.stream()
                .filter(p -> taggablePlayers.contains(p.getId()))
                .min((p1, p2) -> {
            double d1 = Pitch.getDistance(pitchStartX, pitchStartY, p1.getPitchStartX(), p1.getPitchStartY());
            double d2 = Pitch.getDistance(pitchStartX, pitchStartY, p2.getPitchStartX(), p2.getPitchStartY());
            return Double.compare(d1, d2);
        })
                .orElse(null);

        if (player != null) {

        }

        // NOTE: try to tag it
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
            }
            break;
            case Tagged: {
                // NOTE: We have been tagged while trying to reach the home base. We are out!
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
        handle.getStub().election(msg, new GRPCObserverElectionResponse());
    }

    private void forwardElectionToNextPlayer(ElectionMessage msg) {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Forwarding election to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().election(msg, new GRPCObserverElectionResponse());
    }

    private void sendSeekerToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Sending seeker announcement to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        SeekerMessage msg = SeekerMessage.newBuilder().setId(id).build();
        handle.getStub().seeker(msg, new GRPCObserverSeekerResponse());
    }

    private void forwardSeekerToNextPlayer(SeekerMessage msg) {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Forwarding seeker to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().seeker(msg, new GRPCObserverSeekerResponse());
    }

    private void sendTokenToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Sending token to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        TokenMessage msg = TokenMessage.newBuilder().setSeekerId(id).build();
        handle.getStub().token(msg, new GRPCObserverTokenResponse());
    }

    private void forwardTokenToNextPlayer(TokenMessage msg) {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Forwarding token to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().token(msg, new GRPCObserverTokenResponse());
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
}
