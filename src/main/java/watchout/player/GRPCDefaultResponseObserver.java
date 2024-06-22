package watchout.player;

import io.grpc.stub.StreamObserver;

public class GRPCDefaultResponseObserver implements StreamObserver<PlayerPeerServiceOuterClass.Empty> {
    private final String errorMessage;

    public GRPCDefaultResponseObserver(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void onNext(PlayerPeerServiceOuterClass.Empty empty) {
        // NOTE: called when a response from the server arrives
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(errorMessage + ": " + throwable);
    }

    @Override
    public void onCompleted() {
        // NOTE: called when the server has finished sending responses
    }
}
