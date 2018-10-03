package nx.funny.registry.response;

import nx.funny.registry.ServicePosition;

import java.io.Serializable;
import java.util.Set;

public class RegistryResponse implements Serializable {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = 1;

    private int code;
    private String typeName;
    private Set<ServicePosition> addresses;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<ServicePosition> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<ServicePosition> addresses) {
        this.addresses = addresses;
    }
}
