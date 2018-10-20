package nx.funny.transporter.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nx.funny.transporter.MessageDecoder;
import nx.funny.transporter.message.StringMessage;
import nx.funny.transporter.parameter.JsonParameterDeserializer;
import nx.funny.transporter.parameter.Parameter;

/**
 * StringMessage -> InvokerResponse
 */
public class StringMessageInvokerResponseDecoder implements
        MessageDecoder<StringMessage, InvokerResponse> {
    private Gson gson = new GsonBuilder().
            registerTypeAdapter(Parameter.class, new JsonParameterDeserializer()).create();

    @Override
    public InvokerResponse decode(StringMessage message) {
        return gson.fromJson(message.getMessage(), DefaultInvokerResponse.class);
    }
}
