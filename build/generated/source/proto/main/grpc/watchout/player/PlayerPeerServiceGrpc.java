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

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.ElectionMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getElectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "election",
      requestType = watchout.player.PlayerPeerServiceOuterClass.ElectionMessage.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.ElectionMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getElectionMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.ElectionMessage, watchout.player.PlayerPeerServiceOuterClass.Empty> getElectionMethod;
    if ((getElectionMethod = PlayerPeerServiceGrpc.getElectionMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getElectionMethod = PlayerPeerServiceGrpc.getElectionMethod) == null) {
          PlayerPeerServiceGrpc.getElectionMethod = getElectionMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.ElectionMessage, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "election"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.ElectionMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("election"))
              .build();
        }
      }
    }
    return getElectionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.SeekerMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getSeekerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "seeker",
      requestType = watchout.player.PlayerPeerServiceOuterClass.SeekerMessage.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.SeekerMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getSeekerMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.SeekerMessage, watchout.player.PlayerPeerServiceOuterClass.Empty> getSeekerMethod;
    if ((getSeekerMethod = PlayerPeerServiceGrpc.getSeekerMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getSeekerMethod = PlayerPeerServiceGrpc.getSeekerMethod) == null) {
          PlayerPeerServiceGrpc.getSeekerMethod = getSeekerMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.SeekerMessage, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "seeker"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.SeekerMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("seeker"))
              .build();
        }
      }
    }
    return getSeekerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.TokenMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "token",
      requestType = watchout.player.PlayerPeerServiceOuterClass.TokenMessage.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.TokenMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getTokenMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.TokenMessage, watchout.player.PlayerPeerServiceOuterClass.Empty> getTokenMethod;
    if ((getTokenMethod = PlayerPeerServiceGrpc.getTokenMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getTokenMethod = PlayerPeerServiceGrpc.getTokenMethod) == null) {
          PlayerPeerServiceGrpc.getTokenMethod = getTokenMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.TokenMessage, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "token"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.TokenMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("token"))
              .build();
        }
      }
    }
    return getTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getTagMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "tag",
      requestType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getTagMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty, watchout.player.PlayerPeerServiceOuterClass.Empty> getTagMethod;
    if ((getTagMethod = PlayerPeerServiceGrpc.getTagMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getTagMethod = PlayerPeerServiceGrpc.getTagMethod) == null) {
          PlayerPeerServiceGrpc.getTagMethod = getTagMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.Empty, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "tag"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("tag"))
              .build();
        }
      }
    }
    return getTagMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getLeaveRoundMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "leaveRound",
      requestType = watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getLeaveRoundMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage, watchout.player.PlayerPeerServiceOuterClass.Empty> getLeaveRoundMethod;
    if ((getLeaveRoundMethod = PlayerPeerServiceGrpc.getLeaveRoundMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getLeaveRoundMethod = PlayerPeerServiceGrpc.getLeaveRoundMethod) == null) {
          PlayerPeerServiceGrpc.getLeaveRoundMethod = getLeaveRoundMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "leaveRound"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("leaveRound"))
              .build();
        }
      }
    }
    return getLeaveRoundMethod;
  }

  private static volatile io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getEndRoundMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "endRound",
      requestType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      responseType = watchout.player.PlayerPeerServiceOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty,
      watchout.player.PlayerPeerServiceOuterClass.Empty> getEndRoundMethod() {
    io.grpc.MethodDescriptor<watchout.player.PlayerPeerServiceOuterClass.Empty, watchout.player.PlayerPeerServiceOuterClass.Empty> getEndRoundMethod;
    if ((getEndRoundMethod = PlayerPeerServiceGrpc.getEndRoundMethod) == null) {
      synchronized (PlayerPeerServiceGrpc.class) {
        if ((getEndRoundMethod = PlayerPeerServiceGrpc.getEndRoundMethod) == null) {
          PlayerPeerServiceGrpc.getEndRoundMethod = getEndRoundMethod =
              io.grpc.MethodDescriptor.<watchout.player.PlayerPeerServiceOuterClass.Empty, watchout.player.PlayerPeerServiceOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "endRound"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  watchout.player.PlayerPeerServiceOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PlayerPeerServiceMethodDescriptorSupplier("endRound"))
              .build();
        }
      }
    }
    return getEndRoundMethod;
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
    public void election(watchout.player.PlayerPeerServiceOuterClass.ElectionMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getElectionMethod(), responseObserver);
    }

    /**
     */
    public void seeker(watchout.player.PlayerPeerServiceOuterClass.SeekerMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSeekerMethod(), responseObserver);
    }

    /**
     */
    public void token(watchout.player.PlayerPeerServiceOuterClass.TokenMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getTokenMethod(), responseObserver);
    }

    /**
     */
    public void tag(watchout.player.PlayerPeerServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getTagMethod(), responseObserver);
    }

    /**
     */
    public void leaveRound(watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getLeaveRoundMethod(), responseObserver);
    }

    /**
     */
    public void endRound(watchout.player.PlayerPeerServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getEndRoundMethod(), responseObserver);
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
                watchout.player.PlayerPeerServiceOuterClass.ElectionMessage,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_ELECTION)))
          .addMethod(
            getSeekerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.SeekerMessage,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_SEEKER)))
          .addMethod(
            getTokenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.TokenMessage,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_TOKEN)))
          .addMethod(
            getTagMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.Empty,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_TAG)))
          .addMethod(
            getLeaveRoundMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_LEAVE_ROUND)))
          .addMethod(
            getEndRoundMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                watchout.player.PlayerPeerServiceOuterClass.Empty,
                watchout.player.PlayerPeerServiceOuterClass.Empty>(
                  this, METHODID_END_ROUND)))
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
    public void election(watchout.player.PlayerPeerServiceOuterClass.ElectionMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getElectionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void seeker(watchout.player.PlayerPeerServiceOuterClass.SeekerMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSeekerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void token(watchout.player.PlayerPeerServiceOuterClass.TokenMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tag(watchout.player.PlayerPeerServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTagMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leaveRound(watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLeaveRoundMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void endRound(watchout.player.PlayerPeerServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getEndRoundMethod(), getCallOptions()), request, responseObserver);
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
    public watchout.player.PlayerPeerServiceOuterClass.Empty election(watchout.player.PlayerPeerServiceOuterClass.ElectionMessage request) {
      return blockingUnaryCall(
          getChannel(), getElectionMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty seeker(watchout.player.PlayerPeerServiceOuterClass.SeekerMessage request) {
      return blockingUnaryCall(
          getChannel(), getSeekerMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty token(watchout.player.PlayerPeerServiceOuterClass.TokenMessage request) {
      return blockingUnaryCall(
          getChannel(), getTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty tag(watchout.player.PlayerPeerServiceOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getTagMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty leaveRound(watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage request) {
      return blockingUnaryCall(
          getChannel(), getLeaveRoundMethod(), getCallOptions(), request);
    }

    /**
     */
    public watchout.player.PlayerPeerServiceOuterClass.Empty endRound(watchout.player.PlayerPeerServiceOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getEndRoundMethod(), getCallOptions(), request);
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
        watchout.player.PlayerPeerServiceOuterClass.ElectionMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getElectionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> seeker(
        watchout.player.PlayerPeerServiceOuterClass.SeekerMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getSeekerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> token(
        watchout.player.PlayerPeerServiceOuterClass.TokenMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> tag(
        watchout.player.PlayerPeerServiceOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getTagMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> leaveRound(
        watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getLeaveRoundMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<watchout.player.PlayerPeerServiceOuterClass.Empty> endRound(
        watchout.player.PlayerPeerServiceOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getEndRoundMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GREETING = 0;
  private static final int METHODID_ELECTION = 1;
  private static final int METHODID_SEEKER = 2;
  private static final int METHODID_TOKEN = 3;
  private static final int METHODID_TAG = 4;
  private static final int METHODID_LEAVE_ROUND = 5;
  private static final int METHODID_END_ROUND = 6;

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
          serviceImpl.election((watchout.player.PlayerPeerServiceOuterClass.ElectionMessage) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_SEEKER:
          serviceImpl.seeker((watchout.player.PlayerPeerServiceOuterClass.SeekerMessage) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_TOKEN:
          serviceImpl.token((watchout.player.PlayerPeerServiceOuterClass.TokenMessage) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_TAG:
          serviceImpl.tag((watchout.player.PlayerPeerServiceOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_LEAVE_ROUND:
          serviceImpl.leaveRound((watchout.player.PlayerPeerServiceOuterClass.LeaveRoundMessage) request,
              (io.grpc.stub.StreamObserver<watchout.player.PlayerPeerServiceOuterClass.Empty>) responseObserver);
          break;
        case METHODID_END_ROUND:
          serviceImpl.endRound((watchout.player.PlayerPeerServiceOuterClass.Empty) request,
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
              .addMethod(getSeekerMethod())
              .addMethod(getTokenMethod())
              .addMethod(getTagMethod())
              .addMethod(getLeaveRoundMethod())
              .addMethod(getEndRoundMethod())
              .build();
        }
      }
    }
    return result;
  }
}
