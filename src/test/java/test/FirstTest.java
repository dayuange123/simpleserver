package test;

import club.dayuange.server.WebApp;
import club.dayuange.server.WebServer;
import io.netty.bootstrap.ServerBootstrap;
import org.junit.Test;

public class FirstTest {
    @Test
    public void test01() throws Exception {

        WebApp app = new WebApp();
        app.runApp();

    }
}