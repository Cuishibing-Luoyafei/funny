package nx.funny.registry.server.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import nx.funny.registry.request.RegistryRequest;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

public class RequestDecoder extends ByteToMessageDecoder {

    private String readIpAddress(ByteBuf in) {
        StringBuilder ipBuilder = new StringBuilder();
        ipBuilder.append(Short.valueOf(in.readShort()).toString());
        ipBuilder.append(".");
        ipBuilder.append(Short.valueOf(in.readShort()).toString());
        ipBuilder.append(".");
        ipBuilder.append(Short.valueOf(in.readShort()).toString());
        ipBuilder.append(".");
        ipBuilder.append(Short.valueOf(in.readShort()).toString());
        return ipBuilder.toString();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() > 16 && in.getChar(in.readableBytes() - 2) == '\n') {
            RegistryRequest request = new RegistryRequest();
            int operation = in.readInt();
            request.setOperation(operation);
            int port = in.readInt();
            String ipAddress = readIpAddress(in);
            InetSocketAddress address = new InetSocketAddress(ipAddress, port);
            request.setAddress(address);
            CharSequence typeName = in.readCharSequence(in.readableBytes(), Charset.forName("UTF-8"));
            request.setTypeName(typeName.toString());
            out.add(request);
        }
    }
}
