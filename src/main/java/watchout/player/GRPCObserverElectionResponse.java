package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

// TODO: instead of having a lot of observers that basically do the same, use the same default observer.
// TODO: when initializing such observer pass a custom error message.

public class GRPCObserverElectionResponse implements StreamObserver<Empty> {
    @Override
    public void onNext(Empty empty) {
        // NOTE: called when a response from the server arrives
        // NOTE: do nothing
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Failed to send election to next player: " + throwable);
    }

    @Override
    public void onCompleted() {
        // NOTE: do nothing
    }
}
