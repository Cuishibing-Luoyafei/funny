package nx.funny.registry.server.transport.decoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import nx.funny.registry.request.RegistryRequest;

import java.util.List;

import static nx.funny.registry.common.Constant.DEFAULT_CHARSET;

public class RequestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        CharSequence data = in.readCharSequence(in.readableBytes(), DEFAULT_CHARSET);
        RegistryRequest request = new Gson().fromJson(data.toString(), RegistryRequest.class);
        out.add(request);
    }
}
