package cn.xupt.configuration;

import club.dayuange.annotation.FilterCnf;
import club.dayuange.mypacket.filter.Filter;
import club.dayuange.mypacket.filter.FilterChain;
import club.dayuange.mypacket.filter.FilterConfig;
import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.SimpleResponse;

@FilterCnf(value = {"/123.*"},name = "testFilter")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig config) {
        System.out.println("filter 初始化");
    }

    @Override
    public void doFilter(SimpleRequest request, SimpleResponse response, FilterChain chain) {
        System.out.println("哈哈哈");
        chain.doFilter(request,response);
    }



    @Override
    public void destory() {

    }
}