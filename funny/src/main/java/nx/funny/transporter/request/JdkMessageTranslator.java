package nx.funny.transporter.request;

import nx.funny.transporter.message.DefaultMessage;
import nx.funny.transporter.message.Message;

import java.io.*;

public class JdkMessageTranslator<T> extends AbstractMessageTranslator<T> {
    @Override
    protected int getMessageType() {
        return Message.JDK_MESSAGE;
    }

    @Override
    protected byte[] getRequestData(T request) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream op = new ObjectOutputStream(outputStream);
            op.writeObject(request);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public T decode(DefaultMessage message) {
        if (message.getMessageType() != Message.JDK_MESSAGE)
            throw new RuntimeException("不支持的消息类型！");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(message.getMessageBody());
        try {
            ObjectInputStream oi = new ObjectInputStream(inputStream);
            return (T) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
