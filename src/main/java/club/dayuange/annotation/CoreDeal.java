package club.dayuange.annotation;

import java.lang.annotation.*;

/**
 * 标注在类上面，代表这个类是一个处理请求的类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CoreDeal {
    String value() default "";
}
