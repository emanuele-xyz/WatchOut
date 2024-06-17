package watchout.player;

import watchout.common.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.LeaderMessage;

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
        otherPlayersGRPCHandles =  new HashMap<>();
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
}
