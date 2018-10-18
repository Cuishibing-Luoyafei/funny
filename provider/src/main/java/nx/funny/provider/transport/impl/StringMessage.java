package nx.funny.provider.transport.impl;

import nx.funny.provider.transport.Message;

/**
 * 用字符串传递消息
 */
public class StringMessage implements Message {

    private int messageType;
    private int messageLength;
    private byte[] messageBody;

    @Override
    public int getMessageType() {
        return messageType;
    }

    @Override
    public int getMessageLength() {
        return messageLength;
    }

    @Override
    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

}
