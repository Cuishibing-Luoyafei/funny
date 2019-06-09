package nx.funny.transporter.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.message.DefaultMessage;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.message.MessageDecoder;
import nx.funny.transporter.message.MessageEncoder;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.request.JdkMessageTranslator;
import nx.funny.transporter.response.InvokerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class OioClient implements Client {

    private static final int BUFFER_SIZE = 1024;

    @Getter
    @Setter
    private MessageEncoder<InvokerRequest, Message> messageEncoder;

    @Getter
    @Setter
    private MessageDecoder<Message, InvokerResponse> messageDecoder;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private boolean connected = false;

    @Getter
    @Setter
    private int bufferSize = BUFFER_SIZE;
    private ByteBuf buffer = Unpooled.buffer(bufferSize);

    public OioClient() {
        messageEncoder = new JdkMessageTranslator<>();
        messageDecoder = new JdkMessageTranslator<>();
    }

    @Override
    public void connect(String ip, int port) throws IOException {
        if (connected) {
            return;
        }
        socket = new Socket(ip, port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        connected = true;
    }

    @Override
    public InvokerResponse sendRequest(InvokerRequest request) throws IOException {
        if (socket == null) {
            throw new IOException("没有连接到服务器！");
        }
        Message message = messageEncoder.encode(request);
        message.write(buffer);
        buffer.getBytes(0, outputStream, buffer.readableBytes());
        buffer.clear();
        buffer.writeBytes(inputStream, 8);// 先读取类型和长度
        int messageLength = buffer.getInt(4);
        buffer.writeBytes(inputStream, messageLength - 8);
        message = DefaultMessage.read(buffer);
        buffer.clear();
        return messageDecoder.decode(message);
    }

    @Override
    public boolean connected() {
        return connected;
    }

    @Override
    public void close() throws IOException {
        if (socket != null && !socket.isClosed())
            socket.close();
    }
}
