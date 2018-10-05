package nx.funny.registry.client.transport.encoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import nx.funny.registry.request.RegistryRequest;

import static nx.funny.registry.common.Constant.DEFAULT_CHARSET;
import static nx.funny.registry.common.Constant.REQUEST_DELIMITER;

public class RequestEncoder extends MessageToByteEncoder<RegistryRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RegistryRequest request, ByteBuf out) throws Exception {
        encode(request, out);
    }

    public static void encode(RegistryRequest request, ByteBuf out) {
        out.writeCharSequence(new Gson().toJson(request), DEFAULT_CHARSET);
        out.writeCharSequence(REQUEST_DELIMITER, DEFAULT_CHARSET);
    }
}
