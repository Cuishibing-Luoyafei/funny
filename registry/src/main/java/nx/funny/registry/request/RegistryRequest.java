package nx.funny.registry.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceType;

@Getter
@Setter
@ToString
public class RegistryRequest {
    public static final int OPERATION_REGISTER = 0;
    public static final int OPERATION_REMOVE = 1;
    public static final int OPERATION_REMOVE_ALL = 2;
    public static final int OPERATION_RETRIEVE = 3;

    private int operation;
    private ServiceType type;
    private ServicePosition position;

}
