package nx.funny.transporter.message;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用字符串传递消息
 */
@Getter
@Setter
@ToString
public class StringMessage implements Message {

    private int messageType;
    private int messageLength;
    private byte[] messageBody;

    public String getMessage() {
        return new String(messageBody);
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(messageType);
        buffer.writeInt(messageLength);
        buffer.writeBytes(messageBody);
    }

    /**
     * 读取一个消息
     */
    public static StringMessage read(ByteBuf buffer) {
        int messageType = buffer.readInt();
        if (messageType != Message.STRING_MESSAGE)
            throw new RuntimeException("不支持的消息类型！");
        StringMessage message = new StringMessage();
        message.setMessageType(messageType);
        message.setMessageLength(buffer.readInt());
        byte[] body = new byte[message.getMessageLength() - 8];
        buffer.readBytes(body);
        message.setMessageBody(body);
        return message;
    }
}
