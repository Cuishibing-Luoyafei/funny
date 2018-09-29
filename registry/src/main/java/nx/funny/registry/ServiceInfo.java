package nx.funny.registry;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * 服务的信息，类型和位置
 */
public class ServiceInfo implements Serializable {
    private Class<?> clazz;
    private InetSocketAddress address;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceInfo) {
            ServiceInfo info = (ServiceInfo) obj;
            return Objects.equals(info.getClazz(), clazz) &&
                    Objects.equals(info.getAddress(), address);
        }
        return false;
    }
}
