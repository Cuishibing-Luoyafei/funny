package nx.funny.transporter.parameter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonParameterDeserializer implements JsonDeserializer<Parameter> {
    @Override
    public Parameter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject data = json.getAsJsonObject();
        DefaultParameter parameter = new DefaultParameter();
        parameter.setType(data.get("type").getAsString());
        try {
            JsonElement value = data.get("value");
            parameter.setValue(context.deserialize(value, Class.forName(parameter.getType())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return parameter;
    }
}
