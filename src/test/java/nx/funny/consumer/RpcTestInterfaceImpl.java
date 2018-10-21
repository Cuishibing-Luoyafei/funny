package nx.funny.consumer;

import nx.funny.provider.ServiceProvider;

public @ServiceProvider("RpcTestInterface")
class RpcTestInterfaceImpl implements RpcTestInterface{

    @Override
    public String sayHello() {
        return "Hello World";
    }

    @Override
    public Integer sum(Integer a, Integer b) {
        return a + b;
    }
}
