package watchout.player;

import java.util.*;

public class StateManager {
    private static StateManager instance = null;

    public synchronized static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    private State state;
    private final Set<Integer> candidatesThatMayBeUp;

    private StateManager() {
        state = null;
        candidatesThatMayBeUp = new HashSet<>();
    }

    public synchronized State getState() {
        return state;
    }

    public synchronized void idle() {
        state = State.Idle;
    }

    public synchronized void holdElection(List<Integer> candidates) {
        state = State.HoldingElection;
        candidatesThatMayBeUp.clear();
        candidatesThatMayBeUp.addAll(candidates);
    }

    public synchronized void candidateIsDown(int id) {
        // TODO: may be called even in the waiting for leader state
        if (state != State.HoldingElection) throw new IllegalStateException();
        if (!candidatesThatMayBeUp.contains(id)) throw new IllegalArgumentException();
        candidatesThatMayBeUp.remove(id);
    }

    public synchronized boolean areAllCandidatesDown() {
        if (state != State.HoldingElection) throw new IllegalStateException();
        return candidatesThatMayBeUp.isEmpty();
    }

    public synchronized void waitForLeader() {
        if (state != State.HoldingElection) throw new IllegalStateException();
        state = State.WaitingForLeader;
    }

    public synchronized void leader() {
        if (state != State.HoldingElection) throw new IllegalStateException();
        state = State.Leader;
    }
}
