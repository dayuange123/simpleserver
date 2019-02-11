package club.dayuange.mypacket.response;

import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.utils.WebUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefultResponse implements SimpleResponse {
    public List<String> cookies = new ArrayList<>();
    private  SimpleRequest request;
    private final ChannelHandlerContext context;

    public SimpleRequest getRequest() {
        return request;
    }

    public void setRequest(SimpleRequest request) {
        this.request = request;
    }

    public DefultResponse(SimpleRequest request, ChannelHandlerContext ctx) {
        this.request = request;
        this.context = ctx;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    /**
     *
     * @param var1
     */
    @Override
    public void sendRedirect(String var1) {
        WebUtils.sendRedirect(context, var1,this);
    }

    @Override
    public void addCookie(Cookie cookie) {
        ClientCookieEncoder encoder = ClientCookieEncoder.STRICT;
        String jsessionid = encoder.encode(cookie);
        cookies.add(jsessionid);
    }

    @Override
    public void write(String s) {
        WebUtils.writeJson(context,s);
    }
}