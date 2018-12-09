package nx.funny.provider.scanner;

import nx.funny.provider.ServiceProvider;

import java.io.File;
import java.net.URL;
import java.util.*;

public class DefaultServiceProviderScanner implements ServiceProviderScanner {

    @Override
    public List<String> scan(Class<?> baseClass, String... exclude) {
        return scan(baseClass.getPackage().getName(), exclude);
    }

    @Override
    public List<String> scan(String basePackage, String... exclude) {
        if (basePackage == null)
            return Collections.emptyList();
        basePackage = basePackage.trim();
        if ("".equals(basePackage))
            return Collections.emptyList();
        basePackage = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(basePackage);
        if (resource == null || !"file".equals(resource.getProtocol()))
            return Collections.emptyList();
        List<String> classNames = scan(resource.getPath());
        List<String> result = new ArrayList<>();
        Set<String> excludeSet = new HashSet<>(Arrays.asList(exclude));
        classNames.forEach(s -> {
            try {
                Class<?> c = Class.forName(s);
                if (!excludeSet.contains(s) && !c.isInterface() && c.isAnnotationPresent(ServiceProvider.class))
                    result.add(s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return result;
    }

    private List<String> scan(String path) {
        List<String> result = new ArrayList<>();
        File javaFile = new File(path);
        if (!javaFile.isDirectory()) // 可能是其他类型的资源文件
            return Collections.emptyList();
        File[] childFiles = javaFile.listFiles();
        for (File childFile : childFiles) {
            if (childFile.getPath().endsWith("class"))
                result.add(getClassNameOfFilePath(childFile.getPath()));
            else result.addAll(scan(childFile.getPath()));
        }
        return result;
    }

    private String getClassNameOfFilePath(String path) {
        return path.substring(path.indexOf("classes") + 8, path.length() - 6).replace(File.separatorChar, '.');
    }
}
