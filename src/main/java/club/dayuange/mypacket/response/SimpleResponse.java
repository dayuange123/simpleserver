package club.dayuange.mypacket.response;

import io.netty.handler.codec.http.cookie.Cookie;

import java.io.IOException;

public interface SimpleResponse {
    void sendRedirect(String var1) ;
    void addCookie(Cookie var1);
    void write(String s);
}
