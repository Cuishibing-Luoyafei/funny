package nx.funny.registry.client.decoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import nx.funny.registry.response.RegistryResponse;

import java.util.List;

import static nx.funny.registry.common.Constant.DEFAULT_CHARSET;

public class ResponseDecoder extends ByteToMessageDecoder {

    private final RegistryResponse[] responseContainer;

    public ResponseDecoder(RegistryResponse[] responseContainer) {
        this.responseContainer = responseContainer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        CharSequence data = in.readCharSequence(in.readableBytes(), DEFAULT_CHARSET);
        RegistryResponse response = new Gson().fromJson(data.toString(), RegistryResponse.class);
        out.add(response);
        synchronized (responseContainer) {
            responseContainer[0] = response;
        }
        ctx.close();
    }
}
