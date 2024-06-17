package watchout.player;

import watchout.Pitch;
import watchout.common.Player;

import java.util.*;
import java.util.stream.Collectors;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.LeaderMessage;

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

    public synchronized List<Integer> getElectionCandidates() {
        return players.stream().filter(NetworkView::isPlayerCloserToHomeBase).map(Player::getId).collect(Collectors.toList());
    }

    public synchronized void greetAllPlayers() {
        int id = Context.getInstance().getId();
        int port = Context.getInstance().getPort();
        int pitchStartX = Context.getInstance().getPitchStartX();
        int pitchStartY = Context.getInstance().getPitchStartY();

        GreetingRequest request = GreetingRequest.newBuilder().setId(id).setAddress("localhost").setPort(port).setPitchStartX(pitchStartX).setPitchStartY(pitchStartY).build();
        players.forEach(p -> playerGRPCHandles.get(p.getId()).getStub().greeting(request, new GRPCObserverGreetingResponse(p.getId(), p.getAddress(), p.getPort())));
    }

    public synchronized void holdElection() {
        players.stream()
                .filter(NetworkView::isPlayerCloserToHomeBase)
                .forEach(p -> playerGRPCHandles.get(p.getId()).getStub().election(Empty.getDefaultInstance(), new GRPCObserverElectionResponse(p.getId())));
    }

    public synchronized void announceLeader() {
        int id = Context.getInstance().getId();

        LeaderMessage leaderMessage = LeaderMessage.newBuilder().setId(id).build();
        players.forEach(p -> playerGRPCHandles.get(p.getId()).getStub().leader(leaderMessage, new GRPCObserverLeaderResponse(p.getId())));
    }

    // Is the player p closer to the home base than we are?
    private static boolean isPlayerCloserToHomeBase(Player p) {
        int pitchStartX = Context.getInstance().getPitchStartX();
        int pitchStartY = Context.getInstance().getPitchStartY();
        int id = Context.getInstance().getId();

        double myDistanceFromHomeBase = Pitch.getDistanceFromHomeBase(pitchStartX, pitchStartY);
        double otherDistanceFromHomeBase = Pitch.getDistanceFromHomeBase(p.getPitchStartX(), p.getPitchStartY());
        if (myDistanceFromHomeBase != otherDistanceFromHomeBase) {
            return myDistanceFromHomeBase < otherDistanceFromHomeBase;
        } else {
            return id > p.getId();
        }
    }
}
