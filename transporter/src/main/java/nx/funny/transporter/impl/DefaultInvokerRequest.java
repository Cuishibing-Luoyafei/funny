package nx.funny.transporter.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.transporter.InvokerRequest;
import nx.funny.transporter.Parameter;

import java.util.List;

@Getter
@Setter
@ToString
public class DefaultInvokerRequest implements InvokerRequest {

    private String serviceType;
    private String methodName;
    private List<Parameter> parameters;

}
