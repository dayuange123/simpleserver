package club.dayuange.scanner;

import club.dayuange.annotation.RequestMapping;
import club.dayuange.annotation.respondbody;
import club.dayuange.entry.MyRequestMapping;
import club.dayuange.utils.LoggerInitialization;
import club.dayuange.utils.StringUtils;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class ScannerRequestMapping {
    private Logger logger = LoggerInitialization.logger;


    public void doScan(List<Class> classes, Map<String, MyRequestMapping> map) {
        Map<String, MyRequestMapping> map1 = map;
        for (Class aClass : classes) {
            logger.info("find CoreDeal classes:"+classes);
            Class c =  aClass;
            Method[] methods = c.getMethods();
            for (Method method : methods) {
                Annotation[] da = method.getDeclaredAnnotations();
                for (Annotation annotation : da) {
                    if (annotation instanceof RequestMapping) {
                        String value = ((RequestMapping) annotation).value();
                        value=StringUtils.removeIfStartWith(value,"/");
                        MyRequestMapping mapping = new MyRequestMapping(c, method,
                                ((RequestMapping) annotation).engine(), value,
                                ((RequestMapping) annotation).method());
                        map1.put(value, mapping);
                        break;
                    }
                }
            }
        }
    }






}