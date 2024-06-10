package watchout.admin;

import watchout.common.HeartbeatList;

import java.util.ArrayList;
import java.util.List;

public class Heartbeats {
    private static Heartbeats instance = null;

    public synchronized static Heartbeats getInstance() {
        if (instance == null) {
            instance = new Heartbeats();
        }
        return instance;
    }

    private final List<Heartbeat> heartbeats;

    private Heartbeats() {
        this.heartbeats = new ArrayList<>();
    }

    public void addHeartbeats(int id, int timestamp, HeartbeatList heartbeatList) {
        synchronized (heartbeats) {
            for (watchout.common.Heartbeat heartbeat : heartbeatList.getHeartbeats()) {
                heartbeats.add(new Heartbeat(id, heartbeat.getHeartbeat(), heartbeat.getTimestamp()));
            }
        }
    }

    public double getAverageOfLastNHeartbeats(int id, int n) {
        List<Heartbeat> lastNHeartbeats = new ArrayList<>();
        synchronized (heartbeats) {
            for (int i = heartbeats.size() - 1; i >= 0 && lastNHeartbeats.size() < n; i--) {
                Heartbeat heartbeat = heartbeats.get(i);
                if (heartbeat.getPlayerId() == id) {
                    // NOTE: we can just copy the reference instead of copying the object because admin.Heartbeat is immutable
                    lastNHeartbeats.add(heartbeat);
                }
            }
        }
        return lastNHeartbeats.stream().mapToDouble(Heartbeat::getHeartbeat).average().orElse(0.0);
    }

    public double getAverageOfHeartbeatsBetween(int t1, int t2) {
        List<Heartbeat> heartbeatsBetween = new ArrayList<>();
        synchronized (heartbeats) {
            for (Heartbeat heartbeat : heartbeats) {
                if (t1 <= heartbeat.getTimestamp() && heartbeat.getTimestamp() <= t2) {
                    // NOTE: we can just copy the reference instead of copying the object because admin.Heartbeat is immutable
                    heartbeatsBetween.add(heartbeat);
                }
            }
        }
        return heartbeatsBetween.stream().mapToDouble(Heartbeat::getHeartbeat).average().orElse(0.0);
    }
}