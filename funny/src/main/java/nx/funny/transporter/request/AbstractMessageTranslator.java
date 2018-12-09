package nx.funny.transporter.request;

import nx.funny.transporter.message.MessageDecoder;
import nx.funny.transporter.message.MessageEncoder;
import nx.funny.transporter.message.DefaultMessage;
import nx.funny.transporter.message.Message;

public abstract class AbstractMessageTranslator<T> implements MessageEncoder<T, Message>,
        MessageDecoder<Message, T> {

    @Override
    public Message encode(T request) {
        DefaultMessage resultMsg = new DefaultMessage();
        resultMsg.setMessageType(getMessageType());
        byte[] body = getRequestData(request);
        resultMsg.setMessageLength(body.length + 8);
        resultMsg.setMessageBody(body);
        return resultMsg;
    }

    protected abstract int getMessageType();

    protected abstract byte[] getRequestData(T request);

}
