package watchout.player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectionManager {
    private static ElectionManager instance;

    public synchronized static void startElection(List<Integer> betterCandiates) {
        if (instance != null) throw new IllegalStateException("ElectionManager already started");
        instance = new ElectionManager(betterCandiates);
    }

    public synchronized static void stopElection() {
        if (instance == null) throw new IllegalStateException("ElectionManager not started");
        instance = null;
    }

    public synchronized static ElectionManager getInstance() {
        return instance;
    }

    private final Set<Integer> betterCandidates;

    private ElectionManager(List<Integer> betterCandidates) {
        this.betterCandidates = new HashSet<>();
        this.betterCandidates.addAll(betterCandidates);
    }

    public synchronized void removeCandidate(int candidate) {
        if (!betterCandidates.contains(candidate)) throw new IllegalStateException("Candidate " + candidate + " not in candidate list");
        betterCandidates.remove(candidate);
    }

    public synchronized boolean areThereBetterCandidates() {
        return !betterCandidates.isEmpty();
    }
}
