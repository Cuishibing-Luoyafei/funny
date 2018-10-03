package nx.funny.registry.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;
import nx.funny.registry.server.DefaultRequestProcessor;
import nx.funny.registry.server.RequestProcessor;

public class RegistryRequestHandler extends SimpleChannelInboundHandler<RegistryRequest> {

    private RequestProcessor handler;

    public RegistryRequestHandler() {
        handler = new DefaultRequestProcessor();
    }

    public RegistryRequestHandler(RequestProcessor handler) {
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegistryRequest request) throws Exception {
        RegistryResponse response = handler.processRequest(request);
        ctx.pipeline().writeAndFlush(response).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                ctx.close();
            }
        });
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
