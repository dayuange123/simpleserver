package club.dayuange.mypacket.request;

import club.dayuange.exection.RequestTypeExection;
import club.dayuange.mypacket.session.HttpSession;
import io.netty.handler.codec.http.cookie.Cookie;

import java.util.List;

public interface SimpleRequest {
    Object getAttribute(String var1);
    long getContentLength();
    String getContentType();
    String getParameter(String var1) throws RequestTypeExection;
    void setAttribute(String var1, Object var2);
    void removeAttribute(String var1);
    Cookie[] getCookies();
    HttpSession getSession();

}