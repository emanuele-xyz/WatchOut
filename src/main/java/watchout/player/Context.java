package watchout.player;

import watchout.Pitch;
import watchout.common.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;
import watchout.player.PlayerPeerServiceOuterClass.SeekerMessage;

public class Context {
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
    private int seekerId;

    private Context() {
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
            handle.getStub().greeting(request, new GRPCObserverGreetingResponse(p.getId(), p.getAddress(), p.getPort()));
        });
    }

    public synchronized void onGreetingReceive(GreetingRequest greeting) {
        System.out.println(
                "Greeting from player " + greeting.getId()
                        + " - listening at " + greeting.getAddress() + ":" + greeting.getPort()
                        + " - starting at (" + greeting.getPitchStartX() + "," + greeting.getPitchStartY() + ")"
        );
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
            case Hider: {
                // NOTE: game already started
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

            case Seeker: {
                // NOTE: The ring has already elected a seeker (this peer). Thus, block any other election.
                System.out.println("Blocking election for player " + candidateId);
            }
            break;

            case Hider: {
                // NOTE: The ring has already elected a seeker (not this peer). Thus, block any other election.
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
                System.out.println("I'm a hider" + seekerId);
                forwardSeekerToNextPlayer(msg);
            }
            break;

            case Seeker: {
                if (id == seekerId) {
                    // NOTE: It's my own seeker message.
                    // NOTE: start token ring.
                    System.out.println("Game officially started!");
                    sendTokenToNextPlayer();
                } else {
                    throw new IllegalStateException("Multiple seeker messages on the ring");
                }
            }
            break;

            case Hider: {
                throw new IllegalStateException("Hider received seeker message");
            }
            // break;
        }
    }

    private void sendElectionToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("Sending election to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        ElectionMessage msg = ElectionMessage.newBuilder()
                .setId(id)
                .setPitchStartX(pitchStartX)
                .setPitchStartY(pitchStartY)
                .build();
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
        // TODO: to be implemented
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
