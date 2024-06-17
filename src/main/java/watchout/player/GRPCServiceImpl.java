package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceImplBase;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.LeaderMessage;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;

public class GRPCServiceImpl extends PlayerPeerServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<Empty> responseObserver) {
        io.grpc.Context newContext = io.grpc.Context.current().fork();
        io.grpc.Context oldContext = newContext.attach();
        try {
            Context.getInstance().onGreetingReceive(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } finally {
            newContext.detach(oldContext);
        }
    }

    @Override
    public void election(ElectionMessage request, StreamObserver<Empty> responseObserver) {
        io.grpc.Context newContext = io.grpc.Context.current().fork();
        io.grpc.Context oldContext = newContext.attach();
        try {
            Context.getInstance().onElectionReceive(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } finally {
            newContext.detach(oldContext);
        }
    }

    @Override
    public void leader(LeaderMessage request, StreamObserver<Empty> responseObserver) {
        // TODO: to be implemented
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
