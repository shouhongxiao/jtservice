package jtservice.register;

import java.lang.annotation.*;

/**
 * Created by shouh on 2016/8/31.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RegisterAddrAnnotation {
    String name() default "";
    String value() default "";
}
