package nx.funny.transporter.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.transporter.parameter.Parameter;

import java.util.List;


@ToString
public class DefaultInvokerRequest implements InvokerRequest {

    @Getter
    private String type;
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
     * 默认type和typeName相同
     */
    public void setType(Class<?> clazz) {
        type = clazz.getName();
        typeName = type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
