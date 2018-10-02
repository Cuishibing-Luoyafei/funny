package nx.funny.registry.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import nx.funny.registry.request.RegistryRequest;

public class SendRequestHandler extends ChannelInboundHandlerAdapter {

    private RegistryRequest request;

    public SendRequestHandler(RegistryRequest request) {
        this.request = request;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().writeAndFlush(request).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                ctx.close();
            }
        });
    }
}
