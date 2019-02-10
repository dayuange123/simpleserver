package club.dayuange.mypacket.filter;

import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.SimpleResponse;

public interface Filter {
    //config 可以 获取对应的初始化参数，并且还可以添加拦截路径
    void init(FilterConfig config);

    public void doFilter(SimpleRequest request, SimpleResponse response, FilterChain chain);

    void destory();

}
