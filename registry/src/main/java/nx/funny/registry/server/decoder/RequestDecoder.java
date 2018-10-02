package nx.funny.registry.server.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.request.RegistryRequestBody;
import nx.funny.registry.request.RegistryRequestHead;

import java.nio.charset.Charset;
import java.util.List;

public class RequestDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= 10 && in.getChar(in.readableBytes()-2) == '\n') {
            RegistryRequest request = new RegistryRequest();
            int head = in.readInt();
            request.setHead(new RegistryRequestHead(head));
            RegistryRequestBody body = new RegistryRequestBody();
            body.setPort(in.readInt());
            for (int i = 0; i < 4; i++)
                body.getAddress()[i] = in.readShort();
            request.setBody(body);

            CharSequence typeName = in.readCharSequence(in.readableBytes(), Charset.forName("utf-8"));
            body.setTypeName(typeName.toString());
            out.add(request);
        }
    }
}
