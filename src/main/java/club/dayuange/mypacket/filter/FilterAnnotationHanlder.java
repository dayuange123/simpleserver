package club.dayuange.mypacket.filter;

import club.dayuange.annotation.FilterCnf;
import club.dayuange.scanner.MainStartup;
import club.dayuange.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterAnnotationHanlder {
    public static Map<String, String[]> nameAndValue = new HashMap<>();
    public static Map<String, Filter> nameAndClass = new HashMap<>();

    public static void initAllFilter() {
        List<Class> filter = MainStartup.getFilter();
        filter.forEach(aClass -> {
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof FilterCnf) {
                    String name = ((FilterCnf) annotation).name();
                    String[] value = ((FilterCnf) annotation).value();
                    /**
                     * 对value进行处理。
                     * 先把所有的.换成,
                     */
                    for (int i = 0; i < value.length; i++) {
                        if(value[i].endsWith("\\.")){
                            value[i]=value[i].substring(0,value[i].length()-1);
                        }
                        value[i]= StringUtils.replaceD2comma(value[i]);
                        value[i]=value[i].replaceAll("\\*",".*");
                        if(value[i].equals("/")){
                            value[i]=".*";
                        }
                    }
                    nameAndValue.put(name, value);
                    try {
                        nameAndClass.put(name, (Filter) aClass.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}