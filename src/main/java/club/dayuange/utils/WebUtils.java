package club.dayuange.utils;

import club.dayuange.corecontainer.CacheStaticHtml;
import club.dayuange.exection.RequestTypeExection;
import club.dayuange.hanlder.WebServerHandler;
import club.dayuange.mypacket.filter.DefultFilterChain;
import club.dayuange.mypacket.request.DefultRequest;
import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.DefultResponse;
import club.dayuange.mypacket.response.SimpleResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class WebUtils {

    //错误状态处理方法 根据 状态吗 进行处理。
    public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("<h1>Failure: " + status + "</h1>\r\n", CharsetUtil.UTF_8);
        respondHtml(ctx, byteBuf, status);
    }

    public static void senNotFound(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("<h1>Failure: " + status + "</h1>\r\n", CharsetUtil.UTF_8);
        respondHtml(ctx, byteBuf, status);
    }

    private static void respondHtml(ChannelHandlerContext ctx, ByteBuf byteBuf, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    public static void sendRedirect(ChannelHandlerContext ctx, String url, DefultResponse defultResponse) {
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        httpResponse.headers().set(HttpHeaderNames.LOCATION, url);
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);

    }

    public static void writeJson(ChannelHandlerContext context, String s) {
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.copiedBuffer(s.getBytes()));
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        context.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }





    public static void sendHtml(URL uri, ChannelHandlerContext ctx) {
        String path = uri.getFile();

        //这里可以优化的就是 将静态页面缓存起来
        byte[] bytes = CacheStaticHtml.getBytes(path);
        if (bytes == null) {
            bytes = getBytes(uri.getFile());
            CacheStaticHtml.put(path, bytes);
        }
        respondHtml(ctx, Unpooled.copiedBuffer(Arrays.copyOf(bytes, bytes.length)), HttpResponseStatus.OK);
    }

    public static byte[] getBytes(String filePath) {
        File file = new File(filePath);
        ByteArrayOutputStream out = null;
        try {
            FileInputStream in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                out.write(b, 0, b.length);
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] s = out.toByteArray();
        return s;
    }

    public static Map<String, String> getParameter0(FullHttpRequest request) throws RequestTypeExection {
        Map<String, String> parmMap = new HashMap<>();
        HttpMethod method = request.method();
        if (HttpMethod.GET == method) {
            // 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            decoder.parameters().entrySet().forEach(entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap.put(entry.getKey(), entry.getValue().get(0));
            });
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
            decoder.offer(request);
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                try {
                    parmMap.put(data.getName(), data.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new RequestTypeExection("please use post or get request type");
        }
        return parmMap;
    }

    public static void dealFilter(SimpleRequest request, SimpleResponse response, ChannelHandlerContext ctx) throws Exception {
        /**
         * 这里 匹配所有的 过滤器，如果符合 直接调用过滤器的doFilter方法，如果是最后一个，
         */
        DefultFilterChain chain = new DefultFilterChain();
        chain.doFilter(request, response);
        WebServerHandler.dealRequest.mainDealRequest2(request, response, ctx);
    }
}