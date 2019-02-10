package club.dayuange.annotation;

import java.lang.annotation.*;

/**
 * 过滤器的标志
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilterCnf {
    String name() default "";
    //拦截路径
    String[] value() ;

}
