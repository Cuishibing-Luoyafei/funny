package nx.funny.transporter.parameter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonParameterTypeAdapter implements JsonSerializer<Parameter>, JsonDeserializer<Parameter> {
    @Override
    public JsonElement serialize(Parameter src, Type typeOfSrc, JsonSerializationContext context) {
        try {
            JsonObject jsonObject = new JsonObject();
            String type = src.getType();
            jsonObject.addProperty("type",type);
            Class<?> valueClazz = Class.forName(type);
            jsonObject.add("value",context.serialize(src.getValue(),valueClazz));
            return jsonObject;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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
