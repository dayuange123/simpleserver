package club.dayuange.utils;

import club.dayuange.exection.CheckExection;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.List;
import java.util.Map;

public class CheckAccess {

    public static void checkNull(Object o) throws CheckExection {
        if (o == null)
            throw new CheckExection("CheckAccess\n" +
                    "simpleserver.properties failuer,please check your properties!");

    }

    public static String dealUri(FullHttpRequest request) {
        HttpMethod method = request.method();
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.path().substring(1);
    }

    public static boolean checkMapNuLL(Map map,Exception e) throws Exception {
        if(map==null&&e!=null)
            throw e;
        return map == null;
    }
}