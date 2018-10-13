package nx.funny.registry.server.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;
import nx.funny.registry.server.transport.RequestProcessor;

public class RegistryRequestHandler extends SimpleChannelInboundHandler<RegistryRequest> {

    private RequestProcessor handler;

    public RegistryRequestHandler(RequestProcessor handler) {
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegistryRequest request) throws Exception {
        RegistryResponse response = handler.processRequest(request);
        ctx.pipeline().writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public RequestProcessor getHandler() {
        return handler;
    }

    public void setHandler(RequestProcessor handler) {
        this.handler = handler;
    }

}
