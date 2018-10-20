package nx.funny.transporter.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.transporter.parameter.Parameter;

import java.util.List;


@ToString
public class DefaultInvokerRequest implements InvokerRequest {

    @Getter
    private String serviceType;
    @Getter
    @Setter
    private String methodName;
    @Getter
    @Setter
    private List<Parameter> parameters;

    public void setServiceType(Class<?> clazz) {
        serviceType = clazz.getName();
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
