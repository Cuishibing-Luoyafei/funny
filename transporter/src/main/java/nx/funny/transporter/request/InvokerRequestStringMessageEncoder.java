package nx.funny.transporter.request;

import com.google.gson.Gson;
import nx.funny.transporter.MessageEncoder;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.message.StringMessage;


/**
 * InvokerRequest -> StringMessage
 */
public class InvokerRequestStringMessageEncoder implements
        MessageEncoder<InvokerRequest, StringMessage> {
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
