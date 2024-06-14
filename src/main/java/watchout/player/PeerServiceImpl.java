package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.common.Player;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceImplBase;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;

public class PeerServiceImpl extends PlayerPeerServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<Empty> responseObserver) {
        System.out.println(
                "Greeting from player " + request.getId()
                        + " - listening at " + request.getAddress() + ":" + request.getPort()
                        + " - starting at (" + request.getPitchStartX() + "," + request.getPitchStartY() + ")"
        );
        NetworkView.getInstance().registerPlayer(
                new Player(request.getId(), request.getAddress(), request.getPort(), request.getPitchStartX(), request.getPitchStartY())
        );
    }
}
