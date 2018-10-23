package nx.funny.transporter.response;

import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.parameter.Parameter;

import java.io.Serializable;

public interface InvokerResponse extends Serializable {
    Parameter getResult() throws InvokeException;
}
