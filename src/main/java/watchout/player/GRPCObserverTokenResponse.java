package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class GRPCObserverTokenResponse implements StreamObserver<Empty> {
    @Override
    public void onNext(Empty empty) {
        // NOTE: called when a response from the server arrives
        // NOTE: do nothing
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Failed to send token to next player: " + throwable);
    }

    @Override
    public void onCompleted() {
        // NOTE: do nothing
    }
}
