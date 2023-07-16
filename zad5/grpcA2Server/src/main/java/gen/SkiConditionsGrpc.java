package gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: ski_conditions.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SkiConditionsGrpc {

  private SkiConditionsGrpc() {}

  public static final String SERVICE_NAME = "SkiConditions";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gen.SkiConditionsSubscription,
      gen.Conditions> getSubscribeSkiConditionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubscribeSkiConditions",
      requestType = gen.SkiConditionsSubscription.class,
      responseType = gen.Conditions.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<gen.SkiConditionsSubscription,
      gen.Conditions> getSubscribeSkiConditionsMethod() {
    io.grpc.MethodDescriptor<gen.SkiConditionsSubscription, gen.Conditions> getSubscribeSkiConditionsMethod;
    if ((getSubscribeSkiConditionsMethod = SkiConditionsGrpc.getSubscribeSkiConditionsMethod) == null) {
      synchronized (SkiConditionsGrpc.class) {
        if ((getSubscribeSkiConditionsMethod = SkiConditionsGrpc.getSubscribeSkiConditionsMethod) == null) {
          SkiConditionsGrpc.getSubscribeSkiConditionsMethod = getSubscribeSkiConditionsMethod =
              io.grpc.MethodDescriptor.<gen.SkiConditionsSubscription, gen.Conditions>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubscribeSkiConditions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gen.SkiConditionsSubscription.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gen.Conditions.getDefaultInstance()))
              .setSchemaDescriptor(new SkiConditionsMethodDescriptorSupplier("SubscribeSkiConditions"))
              .build();
        }
      }
    }
    return getSubscribeSkiConditionsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SkiConditionsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SkiConditionsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SkiConditionsStub>() {
        @java.lang.Override
        public SkiConditionsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SkiConditionsStub(channel, callOptions);
        }
      };
    return SkiConditionsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SkiConditionsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SkiConditionsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SkiConditionsBlockingStub>() {
        @java.lang.Override
        public SkiConditionsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SkiConditionsBlockingStub(channel, callOptions);
        }
      };
    return SkiConditionsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SkiConditionsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SkiConditionsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SkiConditionsFutureStub>() {
        @java.lang.Override
        public SkiConditionsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SkiConditionsFutureStub(channel, callOptions);
        }
      };
    return SkiConditionsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void subscribeSkiConditions(gen.SkiConditionsSubscription request,
        io.grpc.stub.StreamObserver<gen.Conditions> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubscribeSkiConditionsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SkiConditions.
   */
  public static abstract class SkiConditionsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SkiConditionsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SkiConditions.
   */
  public static final class SkiConditionsStub
      extends io.grpc.stub.AbstractAsyncStub<SkiConditionsStub> {
    private SkiConditionsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkiConditionsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SkiConditionsStub(channel, callOptions);
    }

    /**
     */
    public void subscribeSkiConditions(gen.SkiConditionsSubscription request,
        io.grpc.stub.StreamObserver<gen.Conditions> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSubscribeSkiConditionsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SkiConditions.
   */
  public static final class SkiConditionsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SkiConditionsBlockingStub> {
    private SkiConditionsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkiConditionsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SkiConditionsBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<gen.Conditions> subscribeSkiConditions(
        gen.SkiConditionsSubscription request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSubscribeSkiConditionsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SkiConditions.
   */
  public static final class SkiConditionsFutureStub
      extends io.grpc.stub.AbstractFutureStub<SkiConditionsFutureStub> {
    private SkiConditionsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkiConditionsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SkiConditionsFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE_SKI_CONDITIONS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE_SKI_CONDITIONS:
          serviceImpl.subscribeSkiConditions((gen.SkiConditionsSubscription) request,
              (io.grpc.stub.StreamObserver<gen.Conditions>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSubscribeSkiConditionsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              gen.SkiConditionsSubscription,
              gen.Conditions>(
                service, METHODID_SUBSCRIBE_SKI_CONDITIONS)))
        .build();
  }

  private static abstract class SkiConditionsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SkiConditionsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gen.SkiConditionsProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SkiConditions");
    }
  }

  private static final class SkiConditionsFileDescriptorSupplier
      extends SkiConditionsBaseDescriptorSupplier {
    SkiConditionsFileDescriptorSupplier() {}
  }

  private static final class SkiConditionsMethodDescriptorSupplier
      extends SkiConditionsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SkiConditionsMethodDescriptorSupplier(String methodName) {
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
      synchronized (SkiConditionsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SkiConditionsFileDescriptorSupplier())
              .addMethod(getSubscribeSkiConditionsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
