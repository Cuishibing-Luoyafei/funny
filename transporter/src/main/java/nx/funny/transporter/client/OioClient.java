package nx.funny.transporter.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.MessageDecoder;
import nx.funny.transporter.MessageEncoder;
import nx.funny.transporter.message.StringMessage;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.request.InvokerRequestStringMessageEncoder;
import nx.funny.transporter.response.InvokerResponse;
import nx.funny.transporter.response.StringMessageInvokerResponseDecoder;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class OioClient implements Client, Closeable {
    @Getter
    @Setter
    private MessageEncoder<InvokerRequest, StringMessage> messageEncoder;

    @Getter
    @Setter
    private MessageDecoder<StringMessage, InvokerResponse> messageDecoder;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private static final int BUFFER_SIZE = 1024;
    @Getter
    @Setter
    private int bufferSize = BUFFER_SIZE;
    private ByteBuf buffer = Unpooled.buffer(bufferSize);

    public OioClient() {
        messageEncoder = new InvokerRequestStringMessageEncoder();
        messageDecoder = new StringMessageInvokerResponseDecoder();
    }

    public OioClient(MessageEncoder<InvokerRequest, StringMessage> messageEncoder,
                     MessageDecoder<StringMessage, InvokerResponse> messageDecoder) {
        this.messageEncoder = messageEncoder;
        this.messageDecoder = messageDecoder;
    }

    @Override
    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    @Override
    public InvokerResponse sendRequest(InvokerRequest request) throws IOException {
        if (socket == null) {
            throw new IOException("没有连接到服务器！");
        }
        StringMessage message = messageEncoder.encode(request);
        message.write(buffer);
        buffer.getBytes(0, outputStream, buffer.readableBytes());
        buffer.clear();
        buffer.writeBytes(inputStream, 8);// 先读取类型和长度
        int messageLength = buffer.getInt(4);
        buffer.writeBytes(inputStream, messageLength - 8);
        message = StringMessage.read(buffer);
        buffer.clear();
        return messageDecoder.decode(message);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
