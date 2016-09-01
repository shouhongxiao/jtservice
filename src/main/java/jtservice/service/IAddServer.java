package jtservice.service;

import jtservice.register.RegisterAddrAnnotation;

/**
 * Created by shouh on 2016/8/31.
 */
@RegisterAddrAnnotation(name = "base")
public interface IAddServer {
    @RegisterAddrAnnotation(name = "add", value = "/base/add")
    abstract String add(int x, int y);
}
