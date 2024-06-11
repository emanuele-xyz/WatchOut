package watchout.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class HeartbeatList {
    private List<Heartbeat> heartbeats;
    private int timestamp;

    public HeartbeatList() {
        this(new ArrayList<>(), 0);
    }

    public HeartbeatList(List<Heartbeat> heartbeats, int timestamp) {
        this.heartbeats = heartbeats;
        this.timestamp = timestamp;
    }

    public List<Heartbeat> getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(List<Heartbeat> heartbeats) {
        this.heartbeats = heartbeats;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
