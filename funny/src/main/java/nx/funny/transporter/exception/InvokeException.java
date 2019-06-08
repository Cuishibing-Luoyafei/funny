package nx.funny.transporter.exception;

public class InvokeException extends RuntimeException {
    
    private static final long serialVersionUID = 6203780613351247699L;

    public InvokeException() {
    }

    public InvokeException(String message) {
        super(message);
    }
}
