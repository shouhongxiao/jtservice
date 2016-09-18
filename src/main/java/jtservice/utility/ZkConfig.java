package jtservice.utility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shouh on 2016/9/14.
 */
@Target(value = { ElementType.TYPE })//用于类
@Retention(value = RetentionPolicy.RUNTIME)//注解一直保留到运行时
public @interface ZkConfig {
    /**
     * zookeeper服务器连接信息，如果是集群就配置集群格式的连接字符串
     * @return
     */
    String hosts();
    /**
     * session的超时时间，单位毫秒，默认值5000
     * @return
     */
    int sessionTimeout() default 5000;
}
