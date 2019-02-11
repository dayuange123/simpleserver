##  **1.功能**

> 
> 一个简单的服务器，通过netty，实现了自定义的request和response，并且实现了filter和session功能。
> 
> 需要一个配置文件 名字必须是 simpleserver.properties。
> 配置文件可以指定四个属性:
> port 服务器运行的端口号
> scannerpacket 扫描controller实现了。需要将实现了请求处理的包的路径配置到这里
> configuration filter以及服务器启动的生命周期实现类的实现类所在包路径
> logo  是否打印logo


---



##  2.注解详解



> @CoreDeal 配置在controller类上面，代表是一个处理的类，类似于springmvc中的@Controller
> @FilterCnf 配置在Filter实现类上，tomcat的Filter需要在web.xml配置。在该服务器中，只需要将实现Filter类所在包配置在配置文件中。然后通过注解进行Filter拦截的配置


```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilterCnf {
    String name() default "";
    //拦截路径
    String[] value() ;

}
```
> @RequestMapping 该注解和pringmvc中此注解一个作用。

```
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    //url的路径
    String value() default "";
    //对应的请求类型
    RequestMethod method() default RequestMethod.GET;
    //模板处理引擎
    PageEngine engine() default PageEngine.FreeMarker;
}
```
> @Respondbody 该注解配置在处理请求的方法上，如果配置了该注解，并且设置value为true，将给客户端返回json数据。

```
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Respondbody {
    boolean value() default true;
}
```
## 过滤器以及使用方法

> 然后就是Filter的配置，这里提供一个默认实现，这里和tomcat中的类似，其中value就是拦截的路径。
> 可以使用* 代表任,比如123.* 就匹配123.任何后缀
> /* 可以匹配所有
> 这里如果不放行，后面的过滤器就不会调用。服务器返回。
> 调用顺序为Filter在包装的顺续。


```
@FilterCnf(value = {"/123.*"},name = "testFilter")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig config) {
        System.out.println("filter 初始化");
    }
    @Override
    public void doFilter(SimpleRequest request, SimpleResponse response, FilterChain chain) {
        System.out.println("哈哈哈");
     //   response.sendRedirect("/");
        response.write("{\"1\":12}");
     //   chain.doFilter(request,response);
    }
    @Override
    public void destory() {

    }
}
```
> 我们还可以实现LifeListern接口
在服务器启动和销毁的时候会调用！

```
public class MyLifeListen implements LifeListern {
    public void preInit() {
        System.out.println("服务器启动");
    }

    public void afterInit() {
        System.out.println("服务器结束");
    }
}
```
 
> 我们看一下类似于springmvc的功能
> 这里会将request中的参数，和session封装到对应的属性中。主要类型和参名一致即可。
> 还可以传入request和response。
> 并且会将对应参数封装到实体类中！

```
@CoreDeal
public class MyTestController {
    @RequestMapping(value = "/hello")
   // @Respondbody
    public String hello(SimpleRequest request, Integer aa, boolean b, String s, Student student) throws RequestTypeExection {
//        System.out.println(request.getParameter("s"));
//        System.out.println("Integer test:\t"+aa);
//        System.out.println("boolean test:\t"+b);
//        System.out.println("String test:\t"+s);
//        System.out.println("Student test:\t"+student);
        request.getSession().setAttribute("session","我是session");
     //   System.out.println(request.getSession().getAttributes());
     //   System.out.println(request.getSession());
        request.setAttribute("aaa","华儿是sb");
        return "/";
    }

}
```
## 总结
## 

> 整个实现非常简单，有些东西借鉴别人的实现。整个架构是自己搭起来的。因为第一次搭服务器项目。所以整个项目的架构不是很清楚。庆幸看过部分框架源码。所以一些都是借鉴过来的。本人大三学生，接触时间较短，但一直很坚持，对开源框架充满兴趣，期望自己以后可以有自己的大型项目。
> 本项目有些地方设计不是很友好，是可以进行优化，但是因为本菜最近需要复习春招实习，所以暂且到这里，希望大家可以指出项目的不足，给我发邮箱，我会倍加感谢。邮箱：l695510719@gmail.com 
个人博客 https://dayuange123.club
