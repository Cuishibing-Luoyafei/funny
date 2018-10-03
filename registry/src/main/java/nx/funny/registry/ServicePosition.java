package nx.funny.registry;

import java.io.Serializable;
import java.util.Objects;

public class ServicePosition implements Serializable {
    private String ip;
    private int port;

    public ServicePosition() {
    }

    public ServicePosition(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServicePosition) {
            ServicePosition position = (ServicePosition) obj;
            return Objects.equals(position.ip, ip) && Objects.equals(position.port, port);
        }
        return false;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
