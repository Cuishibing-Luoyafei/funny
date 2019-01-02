package nx.funny.transporter.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author yafei10@staff.weibo.com
 */
public class HeartBeatRequestCodeType {

    private static Map<String, HeartBeatRequestCodeTypeValue> nameMap;
    private static Map<Byte, HeartBeatRequestCodeTypeValue> valueMap;

    static {
        nameMap = new HashMap<>();
        valueMap = new HashMap<>();
        for (HeartBeatRequestCodeTypeValue v : HeartBeatRequestCodeTypeValue.values()) {
            nameMap.put(v.name, v);
            valueMap.put(v.value, v);
        }
    }

    public static HeartBeatRequestCodeTypeValue get(String name) {
        return Optional.ofNullable(nameMap.get(name))
                .orElse(HeartBeatRequestCodeTypeValue.UNKNOWN);
    }

    public static HeartBeatRequestCodeTypeValue get(int value) {
        return Optional.ofNullable(valueMap.get(value))
                .orElse(HeartBeatRequestCodeTypeValue.UNKNOWN);
    }

    public enum HeartBeatRequestCodeTypeValue {

        NORMAL("normal", (byte) 1),
        FIN("fin", (byte) 2),
        MSG("msg", (byte) 3),
        UNKNOWN("unknow", (byte) 0);

        public final String name;
        public final byte value;

        HeartBeatRequestCodeTypeValue(String name, byte value) {
            this.name = name;
            this.value = value;
        }
    }
}
