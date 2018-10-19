package nx.funny.provider.invoker.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.provider.invoker.InvokerRequest;
import nx.funny.provider.invoker.Parameter;

import java.util.List;

@Getter
@Setter
@ToString
public class DefaultInvokerRequest implements InvokerRequest {

    private String serviceType;
    private String methodName;
    private List<Parameter> parameters;

}
