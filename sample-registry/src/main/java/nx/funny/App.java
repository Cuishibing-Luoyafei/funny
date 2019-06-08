package nx.funny;

import nx.funny.registry.HeapServiceRegistry;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 启动注册中心
        HeapServiceRegistry registry = new HeapServiceRegistry("localhost", 9527);
        registry.start();
    }
}
