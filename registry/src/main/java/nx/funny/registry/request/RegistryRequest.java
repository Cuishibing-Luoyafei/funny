package nx.funny.registry.request;

import java.net.InetSocketAddress;

public class RegistryRequest {
    public static final int OPERATION_REGISTER = 0;
    public static final int OPERATION_REMOVE = 1;
    public static final int OPERATION_REMOVE_ALL = 2;
    public static final int OPERATION_RETRIEVE = 3;

    private int operation;
    private InetSocketAddress address;
    private String typeName;

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "RegistryRequest{" +
                "operation=" + operation +
                ", address=" + address +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
