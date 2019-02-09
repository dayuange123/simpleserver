package club.dayuange.hanlder;

import club.dayuange.entry.MyRequestMapping;
import club.dayuange.scanner.MainStartup;
import club.dayuange.utils.CheckAccess;
import club.dayuange.utils.WebUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import javax.swing.text.rtf.RTFEditorKit;
import java.net.URL;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class DealRequest {
    ClassLoader c1 = getClass().getClassLoader();

    /**
     * 请求的核心处理流程
     * 1.首先判断请求合法 不合法返回 400bad
     * 2.获取对应url 判断是否存在，不存在返回404
     * 3.构造request对象和response对象。
     * 4.根据 cookie 进行session的初始化。 使用hashmap ， 这里需要维护过期的session。
     * 5.将session放在 request中。
     * 6.调用用户自定义的过滤器，最后放行，
     * 7.处理对应请求，将请求分发到对应的处理方法。或者说直接返回一些静态文件html
     * 8.通过用户的返回结果对 请求进行响应。这时需要通过用户结果进行响应。
     *
     * @param ctx
     * @param request
     */
    protected void mainDealRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess()) {
            WebUtils.sendError(ctx, BAD_REQUEST);
            return;
        }
        //对路径查找判断是否存在
        String uri = CheckAccess.dealUri(request);
        int res = isEffectivePath(uri);
        if (res == 0) {
            WebUtils.senNotFound(ctx, NOT_FOUND);
            return;
        } else if (res == 1) {
            WebUtils.sendHtml(c1.getResource(uri.equals("") ? "index.html" : uri), ctx);
            return;
        }
        //这里就要对请求进行处理了
    }

    /**
     * @param path
     * @return 0代表 没有notfound
     * 1代表 返回静态html
     * 2 代表是需要处理的请求路径
     */
    private int isEffectivePath(String path) {
        URL resource;
        if (path.length() == 0) {
            //合法 返回index.html
            //这里其实可以 让用户指定首页。
            resource = c1.getResource("index.html");
        } else {
            resource = c1.getResource(path);
        }
        if (resource != null) {
            return 1;
        }
        //从 所有的map中找路径
        if (MainStartup.map.containsKey(path)) return 2;
        return 0;
    }
}