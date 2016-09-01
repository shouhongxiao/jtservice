package jtservice.register;

import com.sun.jersey.core.spi.scanning.PackageNamesScanner;
import com.sun.jersey.core.spi.scanning.Scanner;
import com.sun.jersey.spi.scanning.AnnotationScannerListener;

import java.lang.reflect.Method;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by shouh on 2016/8/31.
 */
public class RedisRegistryService implements IRegistryService {
    @Override
    public void registerPack(String... packages) {
        Scanner scanner = new PackageNamesScanner(checkNotNull(packages));
        final AnnotationScannerListener asl = new RegisterScannerListener();
        scanner.scan(asl);
        Set<Class<?>> sets = asl.getAnnotatedClasses();
        String prefix = "";
        for (Class<?> value : sets) {
            RegisterAddrAnnotation classannotation = value.getAnnotation(RegisterAddrAnnotation.class);
            if(classannotation!=null)
            {
                prefix = classannotation.name();
                register(prefix,value);
            }
        }
    }

    private void register(String prefix,Class<?> clas)
    {
        Method[] methods = clas.getMethods();
        for(Method method:methods)
        {
            RegisterAddrAnnotation registerAddr = method.getAnnotation(RegisterAddrAnnotation.class);
            if(registerAddr!=null)
            {
                JedisContext.getJedis().set(prefix+"."+registerAddr.name(),registerAddr.value());
            }
        }
    }


    @Override
    public void unregister(String name) {
        JedisContext.getJedis().del(name);
    }
}
