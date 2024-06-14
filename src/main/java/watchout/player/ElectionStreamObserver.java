package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class ElectionStreamObserver implements StreamObserver<Empty> {
    private final int otherPlayerID;

    public ElectionStreamObserver(int otherPlayerID) {
        this.otherPlayerID = otherPlayerID;
    }

    @Override
    public void onNext(Empty empty) {
        // NOTE: called when a response from the server arrives
        StateManager.getInstance().waitForLeader();
    }

    @Override
    public void onError(Throwable throwable) {
        StateManager.getInstance().candidateIsDown(otherPlayerID);
        if (StateManager.getInstance().areAllCandidatesDown()) {
            StateManager.getInstance().leader();
        }
    }

    @Override
    public void onCompleted() {
        // NOTE: called when the server finished sending responses
        // NOTE: do nothing
    }
}
