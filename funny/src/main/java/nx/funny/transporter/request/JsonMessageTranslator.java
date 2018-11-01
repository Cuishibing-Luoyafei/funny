package nx.funny.transporter.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nx.funny.transporter.common.GsonUtils;
import nx.funny.transporter.message.DefaultMessage;
import nx.funny.transporter.message.Message;

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
    public T decode(DefaultMessage message) {
        if (message.getMessageType() != Message.JSON_MESSAGE)
            throw new RuntimeException("不支持的消息类型！");
        return gson.fromJson(message.getMessage(), new TypeToken<T>() {
        }.getType());
    }

}
