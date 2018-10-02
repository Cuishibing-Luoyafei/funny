package nx.funny.registry.response;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;

public class RegistryResponse implements Serializable {
    private int code;
    private List<InetSocketAddress> addresses;
    private String typeName;
}
