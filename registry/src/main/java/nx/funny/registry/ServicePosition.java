package nx.funny.registry;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Objects;

public class ServicePosition implements Serializable {
    private InetSocketAddress position;

    public ServicePosition() {
    }

    public ServicePosition(InetSocketAddress position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServicePosition) {
            return Objects.equals(((ServicePosition) obj).position, position);
        }
        return false;
    }

    public InetSocketAddress getPosition() {
        return position;
    }

    public void setPosition(InetSocketAddress position) {
        this.position = position;
    }
}
