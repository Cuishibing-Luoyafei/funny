package nx.funny.registry;

import java.io.Serializable;
import java.util.Objects;

public class ServiceType implements Serializable {
    private String typeName;

    public ServiceType() {
    }

    public ServiceType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ServiceType){
            return Objects.equals(((ServiceType) obj).typeName, typeName);
        }
        return false;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
