package watchout.admin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HeartbeatData {
    private int heartbeat;
    private int timestamp;

    public HeartbeatData() {}

    public HeartbeatData(int heartbeat, int timestamp) {
        this.heartbeat = heartbeat;
        this.timestamp = timestamp;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
