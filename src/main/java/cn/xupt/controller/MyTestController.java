package cn.xupt.controller;

import club.dayuange.annotation.CoreDeal;
import club.dayuange.annotation.RequestMapping;

@CoreDeal
public class MyTestController {
    @RequestMapping(value = "/hello")
    public String hello() {
        return "";
    }

}