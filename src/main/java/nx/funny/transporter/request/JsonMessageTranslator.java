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
        return Message.STRING_MESSAGE;
    }

    @Override
    protected byte[] getRequestData(T request) {
        return gson.toJson(request).getBytes();
    }

    @Override
    public T decode(DefaultMessage message) {
        return gson.fromJson(message.getMessage(), new TypeToken<T>(){}.getType());
    }

}
