package watchout.admin;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private static Players instance = null;

    public synchronized static Players getInstance() {
        if (instance == null) {
            instance = new Players();
        }
        return instance;
    }

    private final List<Player> players;

    private Players() {
        this.players = new ArrayList<>();
    }

    public synchronized boolean isPlayerRegistered(int id) {
        return players.stream().anyMatch(p -> p.getId() == id);
    }

    public synchronized boolean registerPlayer(int id, String address, int port) {
        boolean isPlayerRegistered = isPlayerRegistered(id);
        if (!isPlayerRegistered) {
            players.add(new Player(id, address, port));
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<Player> getPlayers() {
        // NOTE: we can just copy the reference instead of copying the object because admin.Player is immutable
        return new ArrayList<>(players);
    }
}
