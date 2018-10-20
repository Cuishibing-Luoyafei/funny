package nx.funny.transporter.response;

import com.google.gson.Gson;
import nx.funny.transporter.MessageEncoder;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.message.StringMessage;

/**
 * InvokerResponse -> StringMessage
 */
public class InvokerResponseStringMessageEncoder implements MessageEncoder<InvokerResponse, StringMessage> {
    private Gson gson = new Gson();

    @Override
    public StringMessage encode(InvokerResponse invokerResponse) {
        StringMessage resultMsg = new StringMessage();
        resultMsg.setMessageType(Message.STRING_MESSAGE);
        byte[] body = gson.toJson(invokerResponse).getBytes();
        resultMsg.setMessageLength(body.length + 8);
        resultMsg.setMessageBody(body);
        return resultMsg;
    }
}
