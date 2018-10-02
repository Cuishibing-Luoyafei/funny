package nx.funny.registry.request;

import java.io.Serializable;

public class RegistryRequestHead implements Serializable {
    private int type;

    public RegistryRequestHead(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RegistryRequestHead{" +
                "type='" + type + '\'' +
                '}';
    }
}
