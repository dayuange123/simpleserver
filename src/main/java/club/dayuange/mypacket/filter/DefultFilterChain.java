package club.dayuange.mypacket.filter;

import club.dayuange.mypacket.request.DefultRequest;
import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.SimpleResponse;
import club.dayuange.utils.CheckAccess;
import club.dayuange.utils.StringUtils;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


public class DefultFilterChain implements FilterChain {
    private final static Map<String, Filter> nameAndClass = FilterAnnotationHanlder.nameAndClass;
    private final static Map<String, String[]> nameAndValue = FilterAnnotationHanlder.nameAndValue;
    private int index = -1;
    private boolean flag = false;

    @Override
    public void doFilter(SimpleRequest request, SimpleResponse response) {
        index++;
        if (nameAndClass.size() == index) {
            flag = true;
        }
        if (flag) return;
        DefultRequest request1 = (DefultRequest) request;
        FullHttpRequest request0 = request1.getRequest();
        //获取所有的过滤器
        String uri = CheckAccess.dealUri(request0);
        Set<String> set = nameAndValue.keySet();
        List<String> list = new ArrayList<>(set);
        String s1 = list.get(index);
        String[] strings = nameAndValue.get(s1);
        for (String string : strings) {
            //匹配的话就不再遍历
            if (Pattern.matches(string, "/" + StringUtils.replaceD2comma(uri))) {
                Filter filter = nameAndClass.get(s1);
                filter.doFilter(request, response, this);
                //如果这里返回的时候
                if (nameAndClass.size() != index) {
                    //说明未放行
                }
                return;
            }
        }
        //没有匹配到 进行下一个的匹配
        doFilter(request, response);

    }
}