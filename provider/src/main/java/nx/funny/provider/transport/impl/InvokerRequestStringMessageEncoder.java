package nx.funny.provider.transport.impl;

import com.google.gson.Gson;
import nx.funny.provider.invoker.InvokerRequest;
import nx.funny.provider.transport.Message;
import nx.funny.provider.transport.MessageEncoder;

/**
 * InvokerRequest -> StringMessage
 */
public class InvokerRequestStringMessageEncoder implements MessageEncoder<InvokerRequest, StringMessage> {
    private Gson gson = new Gson();

    @Override
    public StringMessage encode(InvokerRequest t) {
        StringMessage resultMsg = new StringMessage();
        resultMsg.setMessageType(Message.STRING_MESSAGE);
        byte[] body = gson.toJson(t).getBytes();
        resultMsg.setMessageLength(body.length + 8);
        resultMsg.setMessageBody(body);
        return resultMsg;
    }

}
