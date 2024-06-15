package watchout.player;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: PlayerPeerService.proto")
public final class PlayerPeerServiceGrpc {

  private PlayerPeerServiceGrpc() {}

  public static final String SERVICE_NAME = "watchout.player.PlayerPeerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.GreetingRequest,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getGreetingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "greeting",
      requestType = watchout.player.PlayerPeerServiceOuterClass.GreetingRequest.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.GreetingRequest,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getGreetingMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.GreetingRequest, watchout.player.PlayerPeerServiceOuterClass.Empty> getGreetingMethod;
    if ((getGreetingMethod = PlayerPeerServiceGrpc.getGreetingMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getGreetingMethod = PlayerPeerServiceGrpc.getGreetingMethod) == null) {
          PlayerPeerServiceGrpc.getGreetingMethod = getGreetingMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.GreetingRequest, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "greeting"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.GreetingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("greeting"))
              .build();
        }
      }
    }
    return getGreetingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getElectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "election",
      requestType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getElectionMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty, watchout.player.PlayerPeerServiceOuterClass.Empty> getElectionMethod;
    if ((getElectionMethod = PlayerPeerServiceGrpc.getElectionMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getElectionMethod = PlayerPeerServiceGrpc.getElectionMethod) == null) {
          PlayerPeerServiceGrpc.getElectionMethod = getElectionMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.Empty, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "election"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("election"))
              .build();
        }
      }
    }
    return getElectionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.LeaderMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getLeaderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "leader",
      requestType = watchout.player.PlayerPeerServiceOuterClass.LeaderMessage.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.LeaderMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getLeaderMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.LeaderMessage, watchout.player.PlayerPeerServiceOuterClass.Empty> getLeaderMethod;
    if ((getLeaderMethod = PlayerPeerServiceGrpc.getLeaderMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getLeaderMethod = PlayerPeerServiceGrpc.getLeaderMethod) == null) {
          PlayerPeerServiceGrpc.getLeaderMethod = getLeaderMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.LeaderMessage, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "leader"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.LeaderMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("leader"))
              .build();
        }
      }
    }
    return getLeaderMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlayerPeerServiceStub newStub(io.grpc.Channel channel) {
    return new PlayerPeerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlayerPeerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PlayerPeerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlayerPeerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PlayerPeerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PlayerPeerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void greeting(watchout.player.PlayerPeerServiceOuterClass.GreetingRequest request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getGreetingMethod(), responseObserver);
    }

    /**
     */
    public void election(watchout.player.PlayerPeerServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getElectionMethod(), responseObserver);
    }

    /**
     */
    public void leader(watchout.player.PlayerPeerServiceOuterClass.LeaderMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getLeaderMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGreetingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.GreetingRequest,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_GREETING)))
          .addMethod(
            getElectionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.Empty,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_ELECTION)))
          .addMethod(
            getLeaderMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.LeaderMessage,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_LEADER)))
          .build();
    }
  }

  /**
   */
  public static final class PlayerPeerServiceStub extends io.grpc.stub.AbstractStub<PlayerPeerServiceStub> {
    private PlayerPeerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlayerPeerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlayerPeerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlayerPeerServiceStub(channel, callOptions);
    }

    /**
     */
    public void greeting(watchout.player.PlayerPeerServiceOuterClass.GreetingRequest request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGreetingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void election(watchout.player.PlayerPeerServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getElectionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leader(watchout.player.PlayerPeerServiceOuterClass.LeaderMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLeaderMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PlayerPeerServiceBlockingStub extends io.grpc.stub.AbstractStub<PlayerPeerServiceBlockingStub> {
    private PlayerPeerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlayerPeerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlayerPeerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlayerPeerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty greeting(watchout.player.PlayerPeerServiceOuterClass.GreetingRequest request) {
      return blockingUnaryCall(
          getChannel(), getGreetingMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty election(watchout.player.PlayerPeerServiceOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getElectionMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty leader(watchout.player.PlayerPeerServiceOuterClass.LeaderMessage request) {
      return blockingUnaryCall(
          getChannel(), getLeaderMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PlayerPeerServiceFutureStub extends io.grpc.stub.AbstractStub<PlayerPeerServiceFutureStub> {
    private PlayerPeerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlayerPeerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlayerPeerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlayerPeerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> greeting(
        watchout.player.PlayerPeerServiceOuterClass.GreetingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGreetingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> election(
        watchout.player.PlayerPeerServiceOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getElectionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> leader(
        watchout.player.PlayerPeerServiceOuterClass.LeaderMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getLeaderMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GREETING = 0;
  private static final int METHODID_ELECTION = 1;
  private static final int METHODID_LEADER = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PlayerPeerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PlayerPeerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GREETING:
          serviceImpl.greeting((watchout.player.PlayerPeerServiceOuterClass.GreetingRequest) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_ELECTION:
          serviceImpl.election((watchout.player.PlayerPeerServiceOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_LEADER:
          serviceImpl.leader((watchout.player.PlayerPeerServiceOuterClass.LeaderMessage) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PlayerPeerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PlayerPeerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return watchout.player.PlayerPeerServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PlayerPeerService");
    }
  }

  private static final class PlayerPeerServiceFileDescriptorSupplier
      extends PlayerPeerServiceBaseDescriptorSupplier {
    PlayerPeerServiceFileDescriptorSupplier() {}
  }

  private static final class PlayerPeerServiceMethodDescriptorSupplier
      extends PlayerPeerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PlayerPeerServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlayerPeerServiceFileDescriptorSupplier())
              .addMethod(getGreetingMethod())
              .addMethod(getElectionMethod())
              .addMethod(getLeaderMethod())
              .build();
        }
      }
    }
    return result;
  }
}
