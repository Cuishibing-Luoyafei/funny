package nx.funny.consumer;

import nx.funny.registry.ServiceInfo;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * 随机选择要调用的服务
 */
public class RandomServiceChooser implements ServiceChooser {

    private Random random = new Random();

    @Override
    public ServiceInfo choose(Set<ServiceInfo> candidates) {
        if (candidates != null && candidates.size() > 0) {
            int r = Math.abs(random.nextInt(candidates.size()));
            Iterator<ServiceInfo> iterator = candidates.iterator();
            int i = 0;
            while (i++ < r)
                iterator.next();
            return iterator.next();
        }
        return null;
    }
}
