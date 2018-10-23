package nx.funny.consumer;

import nx.funny.provider.ServiceProvider;

public @ServiceProvider("RpcTestInterface")
class RpcTestInterfaceImpl implements RpcTestInterface{
    private static int instanceCounter = 0;
    private String name;

    public RpcTestInterfaceImpl() {
        instanceCounter++;
        this.name = "default name";
    }

    public RpcTestInterfaceImpl(String name) {
        instanceCounter++;
        this.name = name;
    }

    @Override
    public String sayHello() {
        return "Hello World "+name+" "+instanceCounter;
    }

    @Override
    public Integer sum(Integer a, Integer b) {
        return a + b;
    }
}
