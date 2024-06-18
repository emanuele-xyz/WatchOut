package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceImplBase;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;
import watchout.player.PlayerPeerServiceOuterClass.SeekerMessage;

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
    public void seeker(SeekerMessage request, StreamObserver<Empty> responseObserver) {
        io.grpc.Context newContext = io.grpc.Context.current().fork();
        io.grpc.Context oldContext = newContext.attach();
        try {
            Context.getInstance().onSeekerReceive(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } finally {
            newContext.detach(oldContext);
        }
    }
}
