package nx.funny.transporter.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nx.funny.transporter.MessageDecoder;
import nx.funny.transporter.message.StringMessage;
import nx.funny.transporter.parameter.JsonParameterDeserializer;
import nx.funny.transporter.parameter.Parameter;

/**
 * StringMessage -> InvokerRequest
 */
public class StringMessageInvokerRequestDecoder implements
        MessageDecoder<StringMessage, InvokerRequest> {
    private Gson gson = new GsonBuilder().
            registerTypeAdapter(Parameter.class, new JsonParameterDeserializer()).create();

    @Override
    public InvokerRequest decode(StringMessage message) {
        return gson.fromJson(message.getMessage(), DefaultInvokerRequest.class);
    }
}
