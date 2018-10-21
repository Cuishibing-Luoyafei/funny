package nx.funny.transporter.request;

import nx.funny.transporter.MessageDecoder;
import nx.funny.transporter.MessageEncoder;
import nx.funny.transporter.message.DefaultMessage;
import nx.funny.transporter.message.Message;

public abstract class AbstractMessageTranslator<T> implements MessageEncoder<T,DefaultMessage>,
        MessageDecoder<DefaultMessage,T> {

    @Override
    public DefaultMessage encode(T request) {
        DefaultMessage resultMsg = new DefaultMessage();
        resultMsg.setMessageType(Message.STRING_MESSAGE);
        byte[] body = getRequestData(request);
        resultMsg.setMessageLength(body.length + 8);
        resultMsg.setMessageBody(body);
        return resultMsg;
    }

    protected abstract int getMessageType();

    protected abstract byte[] getRequestData(T request);
}
