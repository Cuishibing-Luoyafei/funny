package nx.funny.registry.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nx.funny.registry.request.RegistryRequest;

public class RegistryRequestHandler extends SimpleChannelInboundHandler<RegistryRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegistryRequest msg) throws Exception {
        System.out.println("received:"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
