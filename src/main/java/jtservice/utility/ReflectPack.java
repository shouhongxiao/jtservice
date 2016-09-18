package jtservice.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by shouh on 2016/9/14.
 */
public class ReflectPack {
    private static Dictionary<String, Set<Class>> classDict = new Hashtable<>();

    public static Set<Class> getClassesByPackageName(String packageName)
            throws IOException, ClassNotFoundException {
        Set<Class> classes = null;
        classes = classDict.get(packageName);
        if (classes != null) {
            return classes;
        }
        classes = new HashSet<>();
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();

        String path = packageName.replace('.', '/');

        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        if (classes != null) {
            classDict.put(packageName, classes);
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归查找文件夹【即对应的包】下面的所有文件
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + '.'
                        + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName
                        + "."
                        + file.getName().substring(0,
                        file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
