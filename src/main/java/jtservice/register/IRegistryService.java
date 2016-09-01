package jtservice.register;

/**
 * Created by shouh on 2016/8/31.
 */
public interface IRegistryService {
    void registerPack(String... packages);
    void unregister(String name);
}
