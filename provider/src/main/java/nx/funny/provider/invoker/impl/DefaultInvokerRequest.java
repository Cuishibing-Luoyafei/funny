package nx.funny.provider.invoker.impl;

import nx.funny.provider.invoker.InvokerRequest;
import nx.funny.provider.invoker.Parameter;

import java.util.List;

public class DefaultInvokerRequest implements InvokerRequest {

    private String serviceType;
    private String methodName;
    private List<Parameter> parameters;

    @Override
    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}
