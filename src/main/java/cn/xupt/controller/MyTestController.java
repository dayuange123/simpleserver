package cn.xupt.controller;

import club.dayuange.annotation.CoreDeal;
import club.dayuange.annotation.RequestMapping;
import club.dayuange.annotation.Respondbody;
import club.dayuange.exection.RequestTypeExection;
import club.dayuange.mypacket.request.SimpleRequest;
import cn.xupt.pojo.Student;

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