package cn.xupt.configuration;

import club.dayuange.scanner.LifeListern;


public class MyLifeListen implements LifeListern {
    public void preInit() {
        System.out.println("服务器启动");
    }

    public void afterInit() {
        System.out.println("服务器结束");
    }
}