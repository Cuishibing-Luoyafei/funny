package nx.funny.registry.server.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nx.funny.registry.response.RegistryResponse;

public class RegistryResponseHandler extends SimpleChannelInboundHandler<RegistryResponse> {
    private final RegistryResponse[] responseContainer;

    public RegistryResponseHandler(RegistryResponse[] responseContainer) {
        this.responseContainer = responseContainer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegistryResponse response) throws Exception {
        synchronized (responseContainer) {
            responseContainer[0] = response;
            responseContainer.notifyAll();
        }
    }
}
