package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceOuterClass.Empty;


public class GreetingStreamObserver implements StreamObserver<Empty> {
    private final int playerID;
    private final String playerAddress;
    private final int playerPort;

    public GreetingStreamObserver(int playerID, String playerAddress, int playerPort) {
        this.playerID = playerID;
        this.playerAddress = playerAddress;
        this.playerPort = playerPort;
    }

    @Override
    public void onNext(Empty empty) {
        // NOTE: called when a response from the server arrives
        // NOTE: do nothing
        // TODO: should we do something?
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Failed to greet player " + playerID + " on " + playerAddress + ":" + playerPort + ": " + throwable);
    }

    @Override
    public void onCompleted() {
        // NOTE: called when the server finished sending responses
        // TODO: what do we do? (call channel.shutdown()?)
    }
}
