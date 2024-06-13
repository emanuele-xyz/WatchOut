package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceImplBase;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class PlayerPeerServiceImpl extends PlayerPeerServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<Empty> responseObserver) {
        // TODO to be implemented
        System.out.println("Got greeting: " + request.toString()); // TODO: better player formatting
    }
}
