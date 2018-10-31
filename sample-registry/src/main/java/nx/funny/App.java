package nx.funny;

import nx.funny.registry.Registry;
import nx.funny.registry.ServerServiceHeapRegistry;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 启动注册中心
        Registry.init("localhost",9527,new ServerServiceHeapRegistry());
    }
}
