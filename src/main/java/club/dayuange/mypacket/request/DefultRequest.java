package club.dayuange.mypacket.request;

import club.dayuange.mypacket.response.DefultResponse;
import club.dayuange.mypacket.response.SimpleResponse;
import club.dayuange.mypacket.session.HttpSession;
import club.dayuange.mypacket.session.SessionManner;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.*;

import static io.netty.handler.codec.http.HttpHeaderNames.COOKIE;

public class DefultRequest implements SimpleRequest {
    private Map<String, String> parmMap;
    private final FullHttpRequest request;
    private Map<String, Object> attributeMap = new HashMap<>();
    private final SimpleResponse response;

    public FullHttpRequest getRequest() {
        return request;
    }

    public DefultRequest(FullHttpRequest request, Map<String, String> parmMap, SimpleResponse response) {
        this.request = request;
        this.parmMap = parmMap;
        this.response = response;
    }

    @Override
    public Object getAttribute(String var1) {
        return attributeMap.get(var1);
    }

    @Override
    public long getContentLength() {
        return HttpUtil.getContentLength(request);

    }

    @Override
    public String getContentType() {
        return request.headers().get(COOKIE);
    }

    @Override
    public String getParameter(String var1) {
        return parmMap.get(var1);
    }

    @Override
    public void setAttribute(String var1, Object var2) {
        attributeMap.put(var1, var2);
    }

    @Override
    public void removeAttribute(String var1) {
        attributeMap.remove(var1);
    }

    @Override
    public Cookie[] getCookies() {
        String s = request.headers().get(COOKIE);

        ServerCookieDecoder cookieDecoder = ServerCookieDecoder.STRICT;
        Set<Cookie> decode = new HashSet<>();
        if (s != null)
            decode = cookieDecoder.decode(s);
        decode.addAll(((DefultResponse) response).cookies);
        Cookie[] cookies = new Cookie[decode.size()];
        int i = 0;
        for (Cookie cookie : decode) {
            cookies[i++] = cookie;
        }
        return cookies;
    }

    @Override
    public HttpSession getSession() {
        //从cookies容器中获取JSESSIONID  对应的值，
        Cookie[] cookies = getCookies();

        String sessionId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.name().equals("JSESSIONID"))
                    sessionId = cookie.value();
            }
        }
        return SessionManner.getSession(sessionId, this, response);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributeMap;
    }

}