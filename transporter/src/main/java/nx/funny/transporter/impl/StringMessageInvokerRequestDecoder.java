package nx.funny.transporter.impl;

import com.google.gson.*;
import nx.funny.transporter.InvokerRequest;
import nx.funny.transporter.MessageDecoder;
import nx.funny.transporter.Parameter;

import java.lang.reflect.Type;

/**
 * StringMessage -> InvokerRequest
 */
public class StringMessageInvokerRequestDecoder implements
        MessageDecoder<StringMessage, InvokerRequest> {

    private Gson gson = new GsonBuilder().
            registerTypeAdapter(Parameter.class, new JsonDeserializer<Parameter>() {
                @Override
                public Parameter deserialize(JsonElement json, Type typeOfT,
                                             JsonDeserializationContext context) throws JsonParseException {
                    JsonObject data = json.getAsJsonObject();
                    DefaultParameter parameter = new DefaultParameter();
                    parameter.setType(data.get("type").getAsString());
                    try {
                        JsonElement value = data.get("value");
                        if (value.isJsonObject())
                            parameter.setValue(gson.fromJson(value.toString(),
                                    Class.forName(parameter.getType())));
                        else
                            parameter.setValue(gson.fromJson(value.getAsString(),
                                    Class.forName(parameter.getType())));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return parameter;
                }
            }).create();

    @Override
    public InvokerRequest decode(StringMessage message) {
        return gson.fromJson(message.getMessage(), DefaultInvokerRequest.class);
    }
}
