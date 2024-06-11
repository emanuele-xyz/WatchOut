package watchout.admin;

public class Heartbeat {
    private final int playerId;
    private final int heartbeat;
    private final int timestamp;

    public Heartbeat(int playerId, int heartbeat, int timestamp) {
        this.playerId = playerId;
        this.heartbeat = heartbeat;
        this.timestamp = timestamp;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{ playerId: " + playerId + ", heartbeat: " + heartbeat + ", timestamp: " + timestamp + " }";
    }
}
