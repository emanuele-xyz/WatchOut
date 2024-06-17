package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class GRPCObserverLeaderResponse implements StreamObserver<Empty> {
    private final int otherCandidate;

    public GRPCObserverLeaderResponse(int otherCandidate) {
        this.otherCandidate = otherCandidate;
    }

    @Override
    public void onNext(Empty empty) {
        EventHandlers.onLeaderResponse(otherCandidate);
    }

    @Override
    public void onError(Throwable throwable) {
        // TODO: do something?
    }

    @Override
    public void onCompleted() {
        // NOTE: called when the server finished sending responses
        // TODO: do something?
    }
}
