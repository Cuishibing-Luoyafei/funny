package nx.funny.transporter.message;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;

import java.io.Serializable;
import java.util.Objects;

/**
 * heartbeat package
 *
 * @author yafei10@staff.weibo.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeartBeatRequest implements Serializable {

    private static final long serialVersionUID = -7491551613755556239L;

    // 心跳的时间间隔 默认10 * 1000 毫秒
    public static final Integer HREATBEAT_INTERVAL = 10 * 1000;

    // 当出现心跳与注册中心无响应时, 进行里面重试的次数 默认30次
    public static final Integer FAILURE_RETRY_TIME = 30;

    // 失败立马重试的时间间隔 默认1000毫秒
    public static final Integer FAILURE_RETRY_INTERVAL = 1000;

    // 传输码, (1:心跳; 2:断连; 3:携带消息; 待扩充)
    private byte code;

    private int currentTime;

    private long sendInterval;

    // 正常心跳不发消息包, 待特殊情况, 心跳时, 追加消息包 (待扩充)
    private String message;

    private ServiceInfo serviceInfo;

    public static HeartBeatRequest of() {
        return new HeartBeatRequest();
    }

    public HeartBeatRequest code(byte code) {
        this.code = code;
        return this;
    }

    public HeartBeatRequest currentTime(int currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public HeartBeatRequest sendInterval(long sendInterval) {
        this.sendInterval = sendInterval;
        return this;
    }

    public HeartBeatRequest message(String message) {
        this.message = message;
        return this;
    }

    public HeartBeatRequest serviceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartBeatRequest that = (HeartBeatRequest) o;
        return Objects.equals(serviceInfo, that.serviceInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceInfo);
    }
}
