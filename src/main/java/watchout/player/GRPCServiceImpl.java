package watchout.player;

import io.grpc.stub.StreamObserver;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceImplBase;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;
import watchout.player.PlayerPeerServiceOuterClass.Empty;
import watchout.player.PlayerPeerServiceOuterClass.ElectionMessage;
import watchout.player.PlayerPeerServiceOuterClass.SeekerMessage;
import watchout.player.PlayerPeerServiceOuterClass.TokenMessage;
import watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage;

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

    @Override
    public void token(TokenMessage request, StreamObserver<Empty> responseObserver) {
        io.grpc.Context newContext = io.grpc.Context.current().fork();
        io.grpc.Context oldContext = newContext.attach();
        try {
            Context.getInstance().onTokenReceive(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } finally {
            newContext.detach(oldContext);
        }
    }

    @Override
    public void tag(Empty request, StreamObserver<Empty> responseObserver) {
        io.grpc.Context newContext = io.grpc.Context.current().fork();
        io.grpc.Context oldContext = newContext.attach();
        try {
            Context.getInstance().onTagReceive();
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } finally {
            newContext.detach(oldContext);
        }
    }

    @Override
    public void leaveRound(LeaveRoundMessage message, StreamObserver<Empty> responseObserver) {
        io.grpc.Context newContext = io.grpc.Context.current().fork();
        io.grpc.Context oldContext = newContext.attach();
        try {
            Context.getInstance().onLeaveRoundReceive(message);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } finally {
            newContext.detach(oldContext);
        }
    }

    // TODO: endRound
}
