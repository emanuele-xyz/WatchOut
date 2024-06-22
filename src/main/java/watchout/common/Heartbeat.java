package watchout.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Heartbeat {
    private double heartbeat;
    private long timestamp;

    public Heartbeat() {}

    public Heartbeat(double heartbeat, long timestamp) {
        this.heartbeat = heartbeat;
        this.timestamp = timestamp;
    }

    public double getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(double heartbeat) {
        this.heartbeat = heartbeat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("{ heartbeat: %.2f, timestamp: %d }", heartbeat, timestamp);
    }
}
