package nx.funny.registry.request;

import java.io.Serializable;

public class RegistryRequest implements Serializable {
    private RegistryRequestHead head;
    private RegistryRequestBody body;

    public RegistryRequest() {
    }

    public RegistryRequest(RegistryRequestHead head, RegistryRequestBody body) {
        this.head = head;
        this.body = body;
    }

    public RegistryRequestHead getHead() {
        return head;
    }

    public void setHead(RegistryRequestHead head) {
        this.head = head;
    }

    public RegistryRequestBody getBody() {
        return body;
    }

    public void setBody(RegistryRequestBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RegistryRequest{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
