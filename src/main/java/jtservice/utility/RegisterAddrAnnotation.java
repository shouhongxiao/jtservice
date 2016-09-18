package jtservice.utility;

import java.lang.annotation.*;

/**
 * Created by shouh on 2016/9/14.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RegisterAddrAnnotation {
    String name() default "";
    String value() default "";
}
