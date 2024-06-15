package watchout.player;

public class StateManager {
    private static StateManager instance = null;

    public synchronized static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    private State state;

    public synchronized State getState() {
        return state;
    }

    public synchronized void setState(State state) {
        this.state = state;
    }
}
