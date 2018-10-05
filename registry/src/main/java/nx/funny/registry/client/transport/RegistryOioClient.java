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

public class RegistryOioClient implements RegistryClient {

    private Socket socket;

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

    private OutputStream output;
    private InputStream input;

    @Override
    public RegistryResponse sendRequest(RegistryRequest request) {
        ByteBuf in = Unpooled.buffer();
        RequestEncoder.encode(request, in);
        try {
            in.getBytes(0, output, in.readableBytes());
            in.clear();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                in.writeBytes(buffer, 0, len);
                if (len >= 2 && buffer[len - 1] == 10 && buffer[len - 2] == 13)
                    break;
            }
            List<Object> responseContainer = new ArrayList<>(1);
            ResponseDecoder.decode(in, responseContainer);
            return (RegistryResponse) responseContainer.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
