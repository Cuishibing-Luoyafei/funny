package nx.funny.registry.client.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import nx.funny.registry.request.RegistryRequest;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class RequestEncoder extends MessageToByteEncoder<RegistryRequest> {
    private static final char REQUEST_DELIMITER = '\n';
    private static final String DEFAULT_CHARSET = "utf-8";

    @Override
    protected void encode(ChannelHandlerContext ctx, RegistryRequest request, ByteBuf out) throws Exception {
        out.writeInt(request.getOperation());
        InetSocketAddress address = request.getAddress();
        out.writeInt(address.getPort());
        String ipAddress = address.getAddress().getHostAddress();
        String[] ipSection = ipAddress.split("\\.");
        for (int i = 0; i < 4; i++) {
            out.writeShort(Short.valueOf(ipSection[i]));
        }
        out.writeCharSequence(request.getTypeName(), Charset.forName(DEFAULT_CHARSET));
        out.writeChar(REQUEST_DELIMITER);
    }
}
