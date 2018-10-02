package nx.funny.registry.request;

import java.util.Arrays;

public class RegistryRequestBody {
    private int port;
    private short[] address = new short[4];
    private String typeName;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public short[] getAddress() {
        return address;
    }

    public void setAddress(short[] address) {
        this.address = address;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "RegistryRequestBody{" +
                "port=" + port +
                ", address=" + Arrays.toString(address) +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
