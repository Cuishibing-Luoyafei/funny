# Funny使用Java构建的一个远程调用框架

## 注册中心
```
    // 启动注册中心
    Registry.init("localhost",9527,new ServerServiceHeapRegistry());   
```

## 服务提供者
```
    // 根据注册中心地址和自身服务地址生成注册者对象
    Register register = new Register("localhost", 9527, ServerServiceHeapRegistry.class,
            "localhost",9528);
    
    // 注册远程对象
    register.register(Arrays.asList(SampleProviderInterfaceImpl.class,
            SampleProviderInterfaceImpl2.class));
    
    ProviderServer server = new ProviderServer(register);
    
    // 启动服务提供者服务
    server.start();
````
## 服务消费者
````
    // 根据注册中心地址生成代理工厂
    DefaultProxyFactory proxyFactory = new DefaultProxyFactory("localhost", 9527, ServerServiceHeapRegistry.class);

    // 获取远程对象
    SampleProviderInterface service = proxyFactory.getProxy(SampleProviderInterface.class);
    // 调用远程方法
    System.out.println(service.sayHello("FUNNY"));
````