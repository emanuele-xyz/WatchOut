package watchout.player;

import watchout.Pitch;
import watchout.common.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;

import javax.ws.rs.core.StreamingOutput;

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

    private Context() {
    }

    public synchronized State getState() {
        return state;
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

    public synchronized int getPitchStartX() {
        return pitchStartX;
    }

    public synchronized int getPitchStartY() {
        return pitchStartY;
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
        // TODO: to be implemented
        System.out.println("game start notification received");
        if (mayBeSeeker()) {
            System.out.println("I may be the seeker");
            sendElectionToNextPlayer();
            state = State.Voted;
        }
    }

    public synchronized void onElectionReceive(ElectionMessage msg) {
        int candidateId = msg.getId();
        int candidatePitchStartX = msg.getPitchStartX();
        int candidatePitchStartY = msg.getPitchStartY();
        System.out.println("Received election with candidate player " + candidateId);

        switch (state) {
            case Idle: {
                if (isCloserToHomeBaseThan(candidateId, candidatePitchStartX, candidatePitchStartY)) {
                    sendElectionToNextPlayer();
                } else {
                    forwardElectionToNextPlayer(msg);
                }
                state = State.Voted;
            }
            break;

            case Voted: {
                if (id == candidateId) {
                    // NOTE: the election message went around the ring. We are the seeker!
                    System.out.println("I'm the seeker");
                    sendSeekerToNextPlayer();
                    state = State.Seeker;
                } else {
                    if (isCloserToHomeBaseThan(candidateId, candidatePitchStartX, candidatePitchStartY)) {
                        // NOTE: do nothing. We have already voted and our candidate is better than the one we have received
                        System.out.println("Blocking election for player " + candidateId);
                    } else {
                        forwardElectionToNextPlayer(msg);
                    }
                }
            }
            break;

            case Seeker: {
                // TODO: to be implemented
                // TODO: should this happen?
                break;
            }
        }
    }

    private void sendElectionToNextPlayer() {
        int nextPlayerId = findNextPlayerId();
        System.out.println("sending election to player " + nextPlayerId);
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
        System.out.println("forwarding election to player " + nextPlayerId);
        GRPCHandle handle = otherPlayersGRPCHandles.get(nextPlayerId);
        handle.getStub().election(msg, new GRPCObserverElectionResponse());
    }

    private void sendSeekerToNextPlayer() {
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
