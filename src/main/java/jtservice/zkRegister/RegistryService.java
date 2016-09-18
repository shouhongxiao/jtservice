package jtservice.zkRegister;

import com.google.common.collect.Sets;
import jtservice.utility.ReflectPack;
import jtservice.utility.RegisterAddrAnnotation;
import jtservice.utility.ZkConfigParser;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by shouh on 2016/9/14.
 */
public abstract class RegistryService {

    private Set<String> keys = Sets.newHashSet();

    protected Set<String> getKeys() {
        return keys;
    }

    public void build() throws IOException, ClassNotFoundException {
        registerPack();
        init();
    }

    protected abstract void init();

    public void registerPack() throws IOException, ClassNotFoundException {
        String packages = ZkConfigParser.getProperty("packages");
        Set<Class> sets = ReflectPack.getClassesByPackageName(checkNotNull(packages));
        String prefix = "";
        for (Class<?> value : sets) {
            RegisterAddrAnnotation classannotation = value.getAnnotation(RegisterAddrAnnotation.class);
            if (classannotation != null) {
                prefix = classannotation.name();
                register(prefix, value);
            }
        }
    }

    private void register(String prefix, Class<?> clas) {
        Method[] methods = clas.getMethods();
        for (Method method : methods) {
            RegisterAddrAnnotation registerAddr = method.getAnnotation(RegisterAddrAnnotation.class);
            if (registerAddr != null) {
                keys.add(registerAddr.value());
            }
        }
    }


    public static void unregister(String name) {

    }
}
