package nx.funny.registry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@ToString
public class ServiceType implements Serializable {
    private static final long serialVersionUID = 5148421138372099657L;
    private static final Map<String, ServiceType> VALUE_CACHE = new HashMap<>();
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

    public ServiceType name(String name) {
        this.name = name;
        return this;
    }

    public ServiceType typeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    /**
     * 如果name和typeName相同的话就不重复创建对象
     */
    public static final ServiceType valueOf(String name, String typeName) {
        return VALUE_CACHE.computeIfAbsent(name + typeName, k -> new ServiceType(name, typeName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, typeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceType) {
            ServiceType type = (ServiceType) obj;
            return Objects.equals(type.name, name) &&
                    Objects.equals(type.typeName, typeName);
        }
        return false;
    }

}
