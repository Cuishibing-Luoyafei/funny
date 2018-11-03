package nx.funny.transporter.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nx.funny.transporter.common.GsonUtils;
import nx.funny.transporter.message.Message;

import java.nio.charset.Charset;

@Deprecated
public class JsonMessageTranslator<T> extends AbstractMessageTranslator<T> {
    private Gson gson = GsonUtils.gsonInstance();

    @Override
    protected int getMessageType() {
        return Message.JSON_MESSAGE;
    }

    @Override
    protected byte[] getRequestData(T request) {
        return gson.toJson(request).getBytes();
    }

    @Override
    public T decode(Message message) {
        if (message.getMessageType() != Message.JSON_MESSAGE)
            throw new RuntimeException("不支持的消息类型！");
        return gson.fromJson(getMessageContent(message), new TypeToken<T>() {
        }.getType());
    }

    private String getMessageContent(Message message) {
        return new String(message.getMessageBody(), Charset.forName("UTF-8"));
    }

}
