package nx.funny.registry.request;

import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceType;

public class RegistryRequest {
    public static final int OPERATION_REGISTER = 0;
    public static final int OPERATION_REMOVE = 1;
    public static final int OPERATION_REMOVE_ALL = 2;
    public static final int OPERATION_RETRIEVE = 3;

    private int operation;
    private ServiceType type;
    private ServicePosition position;

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public ServicePosition getPosition() {
        return position;
    }

    public void setPosition(ServicePosition position) {
        this.position = position;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }
}
