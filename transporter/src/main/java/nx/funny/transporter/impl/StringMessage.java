package nx.funny.transporter.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.transporter.Message;

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
}
