package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;


public class GreetingStreamObserver implements StreamObserver<Empty> {
    private final int otherPlayerID;
    private final String otherPlayerAddress;
    private final int otherPlayerPort;

    public GreetingStreamObserver(int otherPlayerID, String otherPlayerAddress, int otherPlayerPort) {
        this.otherPlayerID = otherPlayerID;
        this.otherPlayerAddress = otherPlayerAddress;
        this.otherPlayerPort = otherPlayerPort;
    }

    @Override
    public void onNext(Empty empty) {
        // NOTE: called when a response from the server arrives
        // NOTE: do nothing
        // TODO: should we do something?
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Failed to greet player " + otherPlayerID + " on " + otherPlayerAddress + ":" + otherPlayerPort + ": " + throwable);
    }

    @Override
    public void onCompleted() {
        // NOTE: called when the server finished sending responses
        // TODO: what do we do? (call channel.shutdown()?)
    }
}
