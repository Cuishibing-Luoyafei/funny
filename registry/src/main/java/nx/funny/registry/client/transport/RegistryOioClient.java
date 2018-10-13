package nx.funny.registry.client.transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import nx.funny.registry.client.RegistryClient;
import nx.funny.registry.client.transport.decoder.ResponseDecoder;
import nx.funny.registry.client.transport.encoder.RequestEncoder;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static nx.funny.registry.common.Constant.REQUEST_DELIMITER_CR;
import static nx.funny.registry.common.Constant.REQUEST_DELIMITER_NL;

public class RegistryOioClient implements RegistryClient {

    private Socket socket;
    private OutputStream output;
    private InputStream input;

    private static final int BUFFER_SIZE = 512;
    private byte[] byteBuffer = new byte[BUFFER_SIZE];
    private ByteBuf buffer = Unpooled.buffer(BUFFER_SIZE);

    @Override
    public void init(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RegistryResponse sendRequest(RegistryRequest request) {
        buffer.clear();
        RequestEncoder.encode(request, buffer);
        try {
            buffer.getBytes(0, output, buffer.readableBytes());
            buffer.clear();
            int len = 0;
            while ((len = input.read(byteBuffer)) > 0) {
                buffer.writeBytes(byteBuffer, 0, len);
                if (isInputEnd(byteBuffer, len))
                    break;
            }
            List<Object> responseContainer = new ArrayList<>(1);
            ResponseDecoder.decode(buffer, responseContainer);
            return (RegistryResponse) responseContainer.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查数据是否读取完毕
     */
    private boolean isInputEnd(byte[] byteBuffer, int len) {
        return len >= 2
                && byteBuffer[len - 2] == REQUEST_DELIMITER_CR // \r
                && byteBuffer[len - 1] == REQUEST_DELIMITER_NL; // \n
    }

    @Override
    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
