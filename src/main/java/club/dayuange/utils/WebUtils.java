package club.dayuange.utils;

import club.dayuange.corecontainer.CacheStaticHtml;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.*;
import java.net.CacheRequest;
import java.net.URL;
import java.util.Arrays;

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
}