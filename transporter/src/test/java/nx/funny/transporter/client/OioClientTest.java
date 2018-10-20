package nx.funny.transporter.client;

import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.response.InvokerResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class OioClientTest {

    private OioClient client;

    @Before
    public void before() throws IOException {
        client = new OioClient();
        client.connect("localhost", 9527);
    }

    @Test
    public void testSendRequest() throws IOException {
        DefaultInvokerRequest request = new DefaultInvokerRequest();
        request.setType(String.class.getName());
        request.setMethodName("toString");
        InvokerResponse response = client.sendRequest(request);
        System.out.println(response.toString());
        System.out.println((String) (response.getResult().getValue()));
    }
}
