package nx.funny.transporter.server.channlehandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.message.MessageEncoder;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.response.InvokerResponse;

@Setter
@Getter
public class NettyInvokerResponseEncoder extends MessageToByteEncoder<InvokerResponse> {

    private MessageEncoder<InvokerResponse, Message> messageEncoder;

    public NettyInvokerResponseEncoder() {
    }

    public NettyInvokerResponseEncoder(MessageEncoder<InvokerResponse, Message> messageEncoder) {
        this.messageEncoder = messageEncoder;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, InvokerResponse response, ByteBuf out) {
        Message message = messageEncoder.encode(response);
        message.write(out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
