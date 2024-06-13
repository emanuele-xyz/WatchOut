package watchout.player;

import watchout.common.Player;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PlayerRegistry {
    private static PlayerRegistry instance  = null;

    public synchronized static PlayerRegistry getInstance() {
        if (instance == null) {
            instance = new PlayerRegistry();
        }
        return instance;
    }

    private final List<Player> players;
    private final Set<Integer> playerIDs;
    private final Map<Integer, GRPCHandle> playerGRPCHandles;

    private PlayerRegistry() {
        this.players = new ArrayList<>();
        this.playerIDs = new HashSet<>();
        this.playerGRPCHandles = new HashMap<>();
    }

    public synchronized boolean registerPlayer(Player player) {
        boolean isPlayerAlreadyRegistered = playerIDs.contains(player.getId());
        if (!isPlayerAlreadyRegistered) {
            players.add(player);
            playerIDs.add(player.getId());
            playerGRPCHandles.put(player.getId(), new GRPCHandle(player.getAddress(), player.getPort()));
        }
        return !isPlayerAlreadyRegistered;
    }

    public synchronized void forEachHandle(BiConsumer<Player, GRPCHandle> action) {
        for (Player player : players) {
            action.accept(player, playerGRPCHandles.get(player.getId()));
        }
    }
}
