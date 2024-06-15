package watchout.player.responseobservers;

import io.grpc.stub.StreamObserver;
import watchout.player.EventHandlers;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class LeaderResponseObserver implements StreamObserver<Empty> {
    private final int otherCandidate;

    public LeaderResponseObserver(int otherCandidate) {
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
