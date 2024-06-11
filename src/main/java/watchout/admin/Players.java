package watchout.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Players {
    private static Players instance = null;

    public synchronized static Players getInstance() {
        if (instance == null) {
            instance = new Players();
        }
        return instance;
    }

    private final Set<Integer> playerIDs;
    private final List<Player> players;

    private Players() {
        this.playerIDs = new HashSet<>();
        this.players = new ArrayList<>();
    }

    public synchronized boolean isPlayerRegistered(int id) {
        return playerIDs.contains(id);
    }

    public synchronized boolean registerPlayer(int id, String address, int port) {
        boolean isPlayerAlreadyRegistered = isPlayerRegistered(id);
        if (!isPlayerAlreadyRegistered) {
            playerIDs.add(id);
            players.add(new Player(id, address, port));
        }
        return !isPlayerAlreadyRegistered;
    }

    public synchronized List<Player> getPlayers() {
        // NOTE: we can just copy the reference instead of copying the object because admin.Player is immutable
        return new ArrayList<>(players);
    }
}
