package nx.funny.transporter.server;

import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.response.DefaultInvokerResponse;
import org.junit.Before;
import org.junit.Test;

public class NioServerTest {

    private NioServer server;

    @Before
    public void before() {
        server = new NioServer(request -> {
            System.out.println(request.toString());
            DefaultInvokerResponse response = new DefaultInvokerResponse();
            response.setResult(new DefaultParameter(String.class, "OK"));
            return response;
        });
    }

    @Test
    public void testStart() {
        server.start(9527);
    }

}
