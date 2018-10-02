package nx.funny.registry.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nx.funny.registry.request.RegistryRequest;

import java.nio.charset.Charset;

public class SendRequestHandler extends ChannelInboundHandlerAdapter {
    private static final int INITIAL_CAPACITY = 40;
    private static final char REQUEST_DELIMITER = '\n';
    private static final String DEFAULT_CHARSET = "utf-8";
    private RegistryRequest request;

    public SendRequestHandler(RegistryRequest request) {
        this.request = request;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer(INITIAL_CAPACITY);
        byteBuf.writeInt(request.getOpertation());
        byteBuf.writeInt(request.getPort());
        for (short s : request.getAddress())
            byteBuf.writeShort(s);
        byteBuf.writeCharSequence(request.getTypeName(), Charset.forName(DEFAULT_CHARSET));
        byteBuf.writeChar(REQUEST_DELIMITER);
        ctx.writeAndFlush(byteBuf).addListener((future -> {
            ctx.close();
        }));
    }
}
