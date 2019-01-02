package nx.funny.transporter.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author yafei10@staff.weibo.com
 */
public class HeartBeatResponseCodeType {

    private static Map<String, HeartBeatResponseCodeTypeValue> nameMap;
    private static Map<Byte, HeartBeatResponseCodeTypeValue> valueMap;

    static {
        nameMap = new HashMap<>();
        valueMap = new HashMap<>();
        for (HeartBeatResponseCodeTypeValue v : HeartBeatResponseCodeTypeValue.values()) {
            nameMap.put(v.name, v);
            valueMap.put(v.value, v);
        }
    }

    public static HeartBeatResponseCodeTypeValue get(String name) {
        return Optional.ofNullable(nameMap.get(name))
                .orElse(HeartBeatResponseCodeTypeValue.UNKNOWN);
    }

    public static HeartBeatResponseCodeTypeValue get(int value) {
        return Optional.ofNullable(valueMap.get(value))
                .orElse(HeartBeatResponseCodeTypeValue.UNKNOWN);
    }

    public enum HeartBeatResponseCodeTypeValue {
        OK("ok", (byte) 1),
        ERROR("error", (byte) 2),
        FIN("fin", (byte) 3),
        MSG("msg", (byte) 4),
        UNKNOWN("unknow", (byte) 0);

        public final String name;
        public final byte value;

        HeartBeatResponseCodeTypeValue(String name, byte value) {
            this.name = name;
            this.value = value;
        }
    }
}
