package club.dayuange.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    //url的路径
    String value() default "";
    //对应的请求类型
    RequestMethod method() default RequestMethod.GET;

    PageEngine engine() default PageEngine.FreeMarker;
}
