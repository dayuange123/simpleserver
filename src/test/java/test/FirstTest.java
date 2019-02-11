package test;

import club.dayuange.annotation.FilterCnf;
import club.dayuange.server.WebApp;
import club.dayuange.server.WebServer;
import club.dayuange.utils.StringUtils;
import io.netty.bootstrap.ServerBootstrap;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

import java.net.URL;
import java.util.regex.Pattern;

public class FirstTest {
    ClassLoader c1 = getClass().getClassLoader();

    @Test
    public void test01() throws Exception {
//
//        URL url = c1.getResource("index.html");
//        System.out.println(url);
        String string = "/123.*";
        String s = StringUtils.replaceD2comma(string);
        System.out.println(s);
//
//        String s = string.replaceAll("\\.", ",");
//        System.out.println(s);
//        String s1 = s.replaceAll("\\*", ".*");
//        System.out.println(s1);
//        String uri = "/123html";

       // System.out.println(uri.matches(s1));
        int a=0;


    }

    @Test
    public void test02() throws Exception {
        Class<?> aClass = c1.loadClass("cn.xupt.configuration.MyFilter");

        System.out.println(aClass.getAnnotations()[0] instanceof FilterCnf);

    }
    @Test
    public void test03() throws Exception {
        System.out.println(StringUtils.isPacketType(Byte.class));
        Object returnObj = ConvertUtils.convert("asad", boolean.class);
        System.out.println(returnObj.toString());
        System.out.println("aa".toString());
    }
}