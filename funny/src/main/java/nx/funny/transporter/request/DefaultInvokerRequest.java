package nx.funny.transporter.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.transporter.parameter.Parameter;

import java.util.List;


@ToString
public class DefaultInvokerRequest implements InvokerRequest {

    private static final long serialVersionUID = 140427183200373439L;
    
    @Getter
    private String name;
    @Getter
    @Setter
    private String typeName;
    @Getter
    @Setter
    private String methodName;
    @Getter
    @Setter
    private List<Parameter> parameters;

    /**
     * 默认name和typeName相同
     */
    public void setName(Class<?> clazz) {
        setName(clazz.getName());
    }

    public void setName(String name) {
        this.name = name;
        this.typeName = name;
    }
}
