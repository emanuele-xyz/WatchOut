package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceImplBase;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.LeaderMessage;

public class PeerServiceImpl extends PlayerPeerServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<Empty> responseObserver) {
        EventHandlers.onGreeting(request.getId(), request.getAddress(), request.getPort(), request.getPitchStartX(), request.getPitchStartY());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void election(Empty request, StreamObserver<Empty> responseObserver) {
        EventHandlers.onElection();
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void leader(LeaderMessage request, StreamObserver<Empty> responseObserver) {
        int leader = request.getId();
        EventHandlers.onLeader(leader);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
