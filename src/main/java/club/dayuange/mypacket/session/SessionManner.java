package club.dayuange.mypacket.session;


import club.dayuange.hanlder.DealRequest;
import club.dayuange.mypacket.request.DefultRequest;
import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.DefultResponse;
import club.dayuange.mypacket.response.SimpleResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.cookie.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManner {
    //保存session的容器类

    private static Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public static HttpSession getSession(String sessionId, DefultRequest request, SimpleResponse response) {
        return getSession(sessionId, 30 * 60 * 1000, request, response);
    }

    public static HttpSession getSession(String sessionId, long time, DefultRequest request, SimpleResponse response) {
        //没有sessionid 进行创建
        if (sessionId == null) {
            sessionId = createSession(time, request,response);
        }
        HttpSession session = sessionMap.get(sessionId);
       //不为空
        if (session != null) {
            //过期的session 移出并且重新创建
            if (session.getLastAccessedTime() + session.getEffectiveTime() < new Date().getTime()) {
                sessionMap.remove(sessionId);
                sessionId = createSession(time, request, response);
                session = sessionMap.get(sessionId);
            }
        } //如果带有cookie id 但是没取到，说明过期被清理，重新创建一个。
        else {
            sessionId = createSession(time, request, response);
            session = sessionMap.get(sessionId);
        }
        return session;
    }

    public static String createSession(long time, SimpleRequest request, SimpleResponse response) {
        //这里给其初始化一个 session
        String id = UUID.randomUUID().toString();
        //创建cookie
        //将加入的cookie放入 request，最后相应的时候返回给用户
        response.addCookie(new DefaultCookie("JSESSIONID", id));
        HttpSession session = new DefultSession(id, time);
        sessionMap.put(id, session);
        return id;
    }


}