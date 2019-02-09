package club.dayuange.server;

import club.dayuange.scanner.MainStartup;

import java.io.IOException;

public class WebApp {
    public static void main(String[] args) {
        new WebApp().runApp();
    }
    public void runApp(){
        try {
            //启动前的初始化
            Integer port = MainStartup.init(this.getClass().getClassLoader());
            WebServer.run(port);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}