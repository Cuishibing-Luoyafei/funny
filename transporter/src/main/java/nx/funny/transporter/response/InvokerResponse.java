package nx.funny.transporter.response;

import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.parameter.Parameter;

public interface InvokerResponse {
    Parameter getResult() throws InvokeException;
}
