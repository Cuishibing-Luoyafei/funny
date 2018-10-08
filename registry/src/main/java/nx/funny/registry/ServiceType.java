package nx.funny.registry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ServiceType implements Serializable {
	private static final long serialVersionUID = 5148421138372099657L;
	private String name;
    private String typeName;

    public ServiceType() {
    }

    public ServiceType(String name) {
        this(name, name);
    }

    public ServiceType(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, typeName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ServiceType){
            ServiceType type = (ServiceType) obj;
            return Objects.equals(type.name, name) &&
                    Objects.equals(type.typeName, typeName);
        }
        return false;
    }

}
