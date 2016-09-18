package jtservice.service;
import jtservice.utility.RegisterAddrAnnotation;

/**
 * Created by shouh on 2016/8/31.
 */
@RegisterAddrAnnotation(name = "user")
public interface IAddServer {
    @RegisterAddrAnnotation(value = "/user/add")
    abstract String add(int x, int y);
}
