package watchout.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class HeartbeatList {
    private List<Heartbeat> heartbeats;
    private long timestamp;

    public HeartbeatList() {
        this(new ArrayList<>(), 0);
    }

    public HeartbeatList(List<Heartbeat> heartbeats, long timestamp) {
        this.heartbeats = heartbeats;
        this.timestamp = timestamp;
    }

    public List<Heartbeat> getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(List<Heartbeat> heartbeats) {
        this.heartbeats = heartbeats;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
