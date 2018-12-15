package nx.funny.transporter.message;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;

import java.io.Serializable;

/**
 * heartbeat response package
 *
 * @author yafei10@staff.weibo.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeartBeatResponse implements Serializable {

    private static final long serialVersionUID = -7491551613755556239L;

    // 响应码, (1:心跳ok; 2:心跳异常; 3:断连; 4:携带消息包 待扩充)
    private byte code;

    private int currentTime;

    private String message;

    private ServiceInfo registryInfo;

    public static HeartBeatResponse of() {
        return new HeartBeatResponse();
    }

    public HeartBeatResponse code(byte code) {
        this.code = code;
        return this;
    }

    public HeartBeatResponse currentTime(int currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public HeartBeatResponse message(String message) {
        this.message = message;
        return this;
    }

    public HeartBeatResponse registryInfo(ServiceInfo registryInfo) {
        this.registryInfo = registryInfo;
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
