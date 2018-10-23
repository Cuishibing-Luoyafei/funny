package nx.funny.transporter.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nx.funny.transporter.parameter.JsonParameterTypeAdapter;
import nx.funny.transporter.parameter.Parameter;

public class GsonUtils {
    public static Gson gsonInstance(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Parameter.class, new JsonParameterTypeAdapter());
        return builder.create();
    }
}
