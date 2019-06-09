package nx.funny.registry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ServicePosition implements Serializable {
	private static final long serialVersionUID = 5419505658728330368L;
	private String ip;
    private int port;

    public ServicePosition() {
    }

    public ServicePosition(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ServicePosition ip(String ip) {
        this.ip = ip;
        return this;
    }

    public ServicePosition port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ServicePosition) {
            ServicePosition position = (ServicePosition) obj;
            return Objects.equals(position.ip, ip) && Objects.equals(position.port, port);
        }
        return false;
    }

}
