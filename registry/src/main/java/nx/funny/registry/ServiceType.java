package nx.funny.registry;

import java.io.Serializable;
import java.util.Objects;

public class ServiceType implements Serializable {
    private Class<?> type;

    public ServiceType() {
    }

    public ServiceType(Class<?> type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ServiceType){
            return Objects.equals(((ServiceType)obj).type,type);
        }
        return false;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
