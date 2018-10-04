package nx.funny.registry;

import java.io.Serializable;
import java.util.Objects;

public class ServiceType implements Serializable {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
