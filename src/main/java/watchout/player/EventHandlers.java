package watchout.player;

import watchout.common.Player;

import java.util.List;

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
        State state = StateManager.getInstance().getState();
        switch (state) {
            case Idling: {
                onElection();
            }
            case Electing:
            case Playing: {
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }

    public static void onElection() {
        State state = StateManager.getInstance().getState();
        switch (state) {
            case Idling: {
                System.out.println("Leader election started");
                StateManager.getInstance().setState(State.Electing);
                List<Integer> candidates = NetworkView.getInstance().getElectionCandidates();
                if (!candidates.isEmpty()) {
                    ElectionManager.startElection(candidates);
                    NetworkView.getInstance().holdElection();
                } else {
                    System.out.println("I'm the leader");
                    NetworkView.getInstance().announceLeader();
                }
            }
            case Electing:
            case Playing: {
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }

    public static void onElectionResponse(int otherCandidate) {
        // TODO: to be implemented
    }

    // TODO: to be implemented
    public static void onElectionError(int otherCandidate) {
        ElectionManager.getInstance().removeCandidate(otherCandidate);
        boolean thereAreBetterCandidates = ElectionManager.getInstance().areThereBetterCandidates();
        if (!thereAreBetterCandidates) {
            System.out.println("I'm the leader");
            NetworkView.getInstance().announceLeader();
        }
    }

    public static void onLeader(int leader) {
        System.out.println("Elected leader is " + leader);
        // TODO: save leader
        // TODO: start playing
    }

    public static void onLeaderResponse(int otherCandidate) {
        // TODO: to be implemented
        // TODO: once all other players have ACKed back. We know we are the leader and we are starting the game.
    }
}
