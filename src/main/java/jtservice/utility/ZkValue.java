package jtservice.utility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shouh on 2016/9/14.
 */
@Target(value={ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ZkValue {
    /**
     * 配置文件key
     * @return
     */
    String key();
}
