package watchout.player;

import watchout.Pitch;
import watchout.common.Player;

import java.util.*;
import java.util.stream.Collectors;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class NetworkView {
    private static NetworkView instance = null;

    public synchronized static NetworkView getInstance() {
        if (instance == null) {
            instance = new NetworkView();
        }
        return instance;
    }

    private final List<Player> players;
    private final Set<Integer> playerIDs;
    private final Map<Integer, GRPCHandle> playerGRPCHandles;

    private NetworkView() {
        this.players = new ArrayList<>();
        this.playerIDs = new HashSet<>();
        this.playerGRPCHandles = new HashMap<>();
    }

    public synchronized boolean registerPlayer(Player player) {
        boolean isPlayerAlreadyRegistered = playerIDs.contains(player.getId());
        if (!isPlayerAlreadyRegistered) {
            players.add(player);
            playerIDs.add(player.getId());
            playerGRPCHandles.put(player.getId(), new GRPCHandle(player.getAddress(), player.getPort()));
        }
        return !isPlayerAlreadyRegistered;
    }

    public synchronized List<Integer> getElectionCandidateIDs(int id, int pitchStartX, int pitchStartY) {
        return players.stream().filter(p -> isPlayerCloserToHomeBase(id, pitchStartX, pitchStartY, p)).map(Player::getId).collect(Collectors.toList());
    }

    public synchronized void greetAllPlayers(int id, int port, int pitchStartX, int pitchStartY) {
        GreetingRequest request = GreetingRequest.newBuilder().setId(id).setAddress("localhost").setPort(port).setPitchStartX(pitchStartX).setPitchStartY(pitchStartY).build();
        players.forEach(p -> playerGRPCHandles.get(p.getId()).getStub().greeting(request, new GreetingStreamObserver(p.getId(), p.getAddress(), p.getPort())));
    }

    public synchronized void holdElection(int id, int pitchStartX, int pitchStartY) {
        // TODO: to be implemented
        Empty empty = Empty.newBuilder().build();
        players.stream()
                .filter(p -> isPlayerCloserToHomeBase(id, pitchStartX, pitchStartY, p))
                .forEach(p -> playerGRPCHandles.get(p.getId()).getStub().election(empty, new ElectionStreamObserver(p.getId())));
    }

    // Is the player p closer to the home base than we are?
    private static boolean isPlayerCloserToHomeBase(int myID, int myPitchStartX, int myPitchStartY, Player p) {
        double myDistanceFromHomeBase = Pitch.getDistanceFromHomeBase(myPitchStartX, myPitchStartY);
        double otherDistanceFromHomeBase = Pitch.getDistanceFromHomeBase(p.getPitchStartX(), p.getPitchStartY());
        if (myDistanceFromHomeBase != otherDistanceFromHomeBase) {
            return myDistanceFromHomeBase < otherDistanceFromHomeBase;
        } else {
            return myID > p.getId();
        }
    }
}
