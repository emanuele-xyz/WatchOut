package watchout.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class HeartbeatList {
    private List<Heartbeat> heartbeats;

    public HeartbeatList() {}

    public HeartbeatList(List<Heartbeat> heartbeats) {
        this.heartbeats = heartbeats;
    }

    public List<Heartbeat> getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(List<Heartbeat> heartbeats) {
        this.heartbeats = heartbeats;
    }
}
