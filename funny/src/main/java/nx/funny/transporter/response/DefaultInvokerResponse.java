package nx.funny.transporter.response;

import lombok.Setter;
import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.parameter.Parameter;

@Setter
public class DefaultInvokerResponse implements InvokerResponse {

    private Parameter result;
    private InvokeException exception;

    @Override
    public Parameter getResult() throws InvokeException {
        if (exception != null)
            throw exception;
        return result;
    }

    @Override
    public String toString() {
        return "DefaultInvokerResponse(" +
                "result=" + (result == null ? "null" : result.toString()) + ",exception=" + (exception == null ? "null" : exception.toString()) + ")";
    }
}
