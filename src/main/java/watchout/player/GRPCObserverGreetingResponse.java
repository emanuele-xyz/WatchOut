package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;


public class GRPCObserverGreetingResponse implements StreamObserver<Empty> {
    private final int otherPlayerID;
    private final String otherPlayerAddress;
    private final int otherPlayerPort;

    public GRPCObserverGreetingResponse(int otherPlayerID, String otherPlayerAddress, int otherPlayerPort) {
        this.otherPlayerID = otherPlayerID;
        this.otherPlayerAddress = otherPlayerAddress;
        this.otherPlayerPort = otherPlayerPort;
    }

    @Override
    public void onNext(Empty empty) {
        // NOTE: called when a response from the server arrives
        // NOTE: do nothing
    }

    @Override
    public void onError(Throwable throwable) {
        // TODO: onGreetingError
        System.out.println("Failed to greet player " + otherPlayerID + " on " + otherPlayerAddress + ":" + otherPlayerPort + ": " + throwable);
    }

    @Override
    public void onCompleted() {
        // NOTE: called when the server finished sending responses
    }
}
