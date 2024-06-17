package watchout.player;

import watchout.common.Player;

public class EventHandlers {
    public static void onGreeting(int id, String address, int port, int pitchStartX, int pitchStartY) {
        System.out.println(
                "Greeting from player " + id
                        + " - listening at " + address + ":" + port
                        + " - starting at (" + pitchStartX + "," + pitchStartY + ")"
        );
        NetworkView.getInstance().registerPlayer(new Player(id, address, port, pitchStartX, pitchStartY));
    }

    public static void onAdminClientMessage(String message) {
        System.out.println("Received message from admin client: " + message);
    }

    public static void onGameStart() {
        // TODO: to be implemented
        System.out.println("game start notification received");
    }

    public static void onElection() {
        // TODO: to be implemented
        System.out.println("election message received");
    }

    public static void onElectionResponse(int otherCandidate) {
        // TODO: to be implemented
        System.out.println("election response sent");
    }

    public static void onElectionError(int otherCandidate) {
        // TODO: to be implemented
        System.out.println("election error");
    }

    public static void onLeader(int leader) {
        // TODO: to be implemented
        System.out.println("leader message received");
    }

    public static void onLeaderResponse(int otherCandidate) {
        // TODO: to be implemented
        System.out.println("leader response sent");
    }
}
