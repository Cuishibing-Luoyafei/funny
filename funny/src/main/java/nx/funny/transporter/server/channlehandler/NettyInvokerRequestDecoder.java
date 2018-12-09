package nx.funny.transporter.server.channlehandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.message.MessageDecoder;
import nx.funny.transporter.message.DefaultMessage;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.request.InvokerRequest;

import java.util.List;

@Setter
@Getter
public class NettyInvokerRequestDecoder extends ByteToMessageDecoder {
    private MessageDecoder<Message, InvokerRequest> messageDecoder;

    public NettyInvokerRequestDecoder() {
    }

    public NettyInvokerRequestDecoder(MessageDecoder<Message, InvokerRequest> messageDecoder) {
        this.messageDecoder = messageDecoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // message类型和body长度
        if (in.readableBytes() >= 8) {
            // int messageType = in.getInt(0);
            int messageLength = in.getInt(4);
            if (in.readableBytes() >= messageLength) {
                DefaultMessage message = DefaultMessage.read(in);
                out.add(messageDecoder.decode(message));
            }
        }
    }
}
