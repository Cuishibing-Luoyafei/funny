package nx.funny.registry.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.registry.ServiceInfo;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
public class RegistryResponse implements Serializable {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = 1;

    private int code;
    private String msg;
    private Set<ServiceInfo> infos;

}
