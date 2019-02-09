package test;

import club.dayuange.server.WebApp;
import club.dayuange.server.WebServer;
import io.netty.bootstrap.ServerBootstrap;
import org.junit.Test;

import java.net.URL;

public class FirstTest {
    ClassLoader c1 = getClass().getClassLoader();
    @Test
    public void test01() throws Exception {

        URL url = c1.getResource("index.html");
        System.out.println(url);

    }
}