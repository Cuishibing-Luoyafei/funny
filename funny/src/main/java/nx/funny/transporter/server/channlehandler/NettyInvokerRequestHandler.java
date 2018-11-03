package nx.funny.transporter.server.channlehandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.response.InvokerResponse;
import nx.funny.transporter.server.InvokerRequestProcessor;

@Getter
@Setter
public class NettyInvokerRequestHandler extends SimpleChannelInboundHandler<InvokerRequest> {
    private InvokerRequestProcessor requestProcessor;

    public NettyInvokerRequestHandler() {
    }

    public NettyInvokerRequestHandler(InvokerRequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvokerRequest request) throws Exception {
        InvokerResponse invokerResponse = requestProcessor.processRequest(request);
        ctx.pipeline().writeAndFlush(invokerResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
