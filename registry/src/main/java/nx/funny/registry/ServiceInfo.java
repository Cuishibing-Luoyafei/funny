package nx.funny.registry;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * 服务的信息，类型和位置
 */
public class ServiceInfo implements Serializable {
    private Class<?> clazz;
    private InetAddress position;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public InetAddress getPosition() {
        return position;
    }

    public void setPosition(InetAddress position) {
        this.position = position;
    }
}
