package watchout.player;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import watchout.player.PlayerPeerServiceGrpc.PlayerPeerServiceStub;

public class GRPCHandle {
    private final ManagedChannel channel;
    private final PlayerPeerServiceStub stub;

    public GRPCHandle(String address, int port) {
        this.channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
        this.stub = PlayerPeerServiceGrpc.newStub(channel);
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public PlayerPeerServiceStub getStub() {
        return stub;
    }
}
