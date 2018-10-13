package nx.funny.registry.server.transport.encoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import nx.funny.registry.response.RegistryResponse;

import static nx.funny.registry.common.Constant.DEFAULT_CHARSET;
import static nx.funny.registry.common.Constant.REQUEST_DELIMITER;

public class ResponseEncoder extends MessageToByteEncoder<RegistryResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RegistryResponse response, ByteBuf out) throws Exception {
        out.writeCharSequence(new Gson().toJson(response), DEFAULT_CHARSET);
        out.writeCharSequence(REQUEST_DELIMITER, DEFAULT_CHARSET);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
