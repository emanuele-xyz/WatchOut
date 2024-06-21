package watchout.admin;

public class Heartbeat {
    private final int playerId;
    private final double heartbeat;
    private final long timestamp;

    public Heartbeat(int playerId, double heartbeat, long timestamp) {
        this.playerId = playerId;
        this.heartbeat = heartbeat;
        this.timestamp = timestamp;
    }

    public int getPlayerId() {
        return playerId;
    }

    public double getHeartbeat() {
        return heartbeat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{ playerId: " + playerId + ", heartbeat: " + heartbeat + ", timestamp: " + timestamp + " }";
    }
}
