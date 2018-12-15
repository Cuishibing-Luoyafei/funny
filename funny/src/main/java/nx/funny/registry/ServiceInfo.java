package nx.funny.registry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 服务的信息，类型和位置
 */
@Getter
@Setter
@ToString
public class ServiceInfo implements Serializable {
	private static final long serialVersionUID = 5850624516850445498L;
	private ServiceType type;
    private ServicePosition position;

    public ServiceInfo() {
    }

    public ServiceInfo(ServiceType type, ServicePosition position) {
        this.type = type;
        this.position = position;
    }

    public ServiceInfo(String name, String ip, int port) {
        this(new ServiceType(name),
                new ServicePosition(ip, port));
    }

    public ServiceInfo type(ServiceType type) {
        this.type = type;
        return this;
    }

    public ServiceInfo position(ServicePosition position) {
        this.position = position;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceInfo) {
            ServiceInfo info = (ServiceInfo) obj;
            return Objects.equals(info.getType(), type) &&
                    Objects.equals(info.getPosition(), position);
        }
        return false;
    }

}
