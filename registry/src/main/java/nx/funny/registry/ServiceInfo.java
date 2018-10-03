package nx.funny.registry;

import java.io.Serializable;
import java.util.Objects;

/**
 * 服务的信息，类型和位置
 */
public class ServiceInfo implements Serializable {

    private ServiceType type;
    private ServicePosition position;

    public ServiceInfo() {
    }

    public ServiceInfo(ServiceType type, ServicePosition position) {
        this.type = type;
        this.position = position;
    }

    public ServiceInfo(String typeName, String ip, int port) {
        this(new ServiceType(typeName),
                new ServicePosition(ip, port));
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

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public ServicePosition getPosition() {
        return position;
    }

    public void setPosition(ServicePosition position) {
        this.position = position;
    }
}
