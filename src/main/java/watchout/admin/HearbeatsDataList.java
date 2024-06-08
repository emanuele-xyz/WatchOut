package watchout.admin;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class HearbeatsDataList {
    private List<HeartbeatData> heartbeats;
    private int timestamp;

    public HearbeatsDataList() {}

    public List<HeartbeatData> getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(List<HeartbeatData> heartbeats) {
        this.heartbeats = heartbeats;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
