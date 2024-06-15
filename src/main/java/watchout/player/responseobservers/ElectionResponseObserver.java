package watchout.player.responseobservers;

import io.grpc.stub.StreamObserver;
import watchout.player.EventHandlers;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class ElectionResponseObserver implements StreamObserver<Empty> {
    private final int otherCandidate;

    public ElectionResponseObserver(int otherCandidate) {
        this.otherCandidate = otherCandidate;
    }

    @Override
    public void onNext(Empty empty) {
        EventHandlers.onElectionResponse(otherCandidate);
    }

    @Override
    public void onError(Throwable throwable) {
        EventHandlers.onElectionError(otherCandidate);
    }

    @Override
    public void onCompleted() {
        // NOTE: do nothing
    }
}
