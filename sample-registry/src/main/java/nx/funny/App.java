package nx.funny;

import nx.funny.registry.Registry;
import nx.funny.registry.ServerServiceHeapRegistry;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Registry.init("localhost",9527,new ServerServiceHeapRegistry());
    }
}
