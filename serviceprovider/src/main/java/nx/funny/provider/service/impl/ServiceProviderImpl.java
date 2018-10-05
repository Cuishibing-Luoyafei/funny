package nx.funny.provider.service.impl;

import nx.funny.annotation.FunnyServiceProvider;
import nx.funny.provider.service.IServiceProvider;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;
import nx.funny.registry.client.ClientServiceRegistry;
import nx.funny.registry.client.RegistryClient;
import nx.funny.registry.client.transport.RegistryNettyClient;

/**
 *
 * @author luoyafei
 * @date 2018-10-05
 */
@FunnyServiceProvider(serviceName = "hello-service", serverServiceRegistryPath = "127.0.0.1:9527")
public class ServiceProviderImpl implements IServiceProvider {

    private ServiceRegistry serviceRegistry;
    private RegistryClient registryClient;
    private String serviceName;
    private String serverIp;
    private Integer serverPort;

    public ServiceProviderImpl() {
        FunnyServiceProvider provider = this.getClass().getAnnotation(FunnyServiceProvider.class);
        serviceName = provider.serviceName();
        String serverServiceRegistryPath = provider.serverServiceRegistryPath();
        serverIp = serverServiceRegistryPath.split(":")[0];
        serverPort = Integer.parseInt(serverServiceRegistryPath.split(":")[1]);
        registryClient = new RegistryNettyClient();
        registryClient.init(serverIp, serverPort);
        serviceRegistry = new ClientServiceRegistry(registryClient);
    }

    @Override
    public boolean register() {
        serviceRegistry.register(
                new ServiceInfo(
                        new ServiceType(serviceName),
                        new ServicePosition(serverIp, serverPort)
                )
        );
        return true;
    }

    @Override
    public boolean ping() {
        // TODO: 2018/10/5 增加心跳检测
        return true;
    }

}
