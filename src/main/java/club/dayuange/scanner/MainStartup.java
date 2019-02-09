package club.dayuange.scanner;

import club.dayuange.entry.DefaultListen;
import club.dayuange.entry.MyRequestMapping;
import club.dayuange.entry.SimpleProperties;
import club.dayuange.exection.CheckExection;
import club.dayuange.utils.CheckAccess;
import club.dayuange.utils.LoggerInitialization;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主扫描启动，主要一些生命周期方法
 */
public class MainStartup {

    private static SimpleProperties properties = null;
    private static Logger logger = LoggerInitialization.logger;
    private static LifeListern lifeListern = new DefaultListen();
    private static List<Object> strings = null;
    private static List<Object> classes = null;
    private static ClassLoader classLoader = null;
    private static Map<String, MyRequestMapping> map=new HashMap<String, MyRequestMapping>();
    public static Integer init(ClassLoader c) throws IOException, ClassNotFoundException {
        //首先加载配置类
        classLoader = c;
        ScannerConf scan = new ScannerConf(classLoader);
        properties = scan.loadProperties();
        try {
            CheckAccess.checkNull(properties);
        } catch (CheckExection checkExection) {
            logger.warn(checkExection.getMessage());
        }
        // logger.info(properties);
        //print logo
        if (properties.isLogo()) {
            LoggerInitialization.printLogo();
        }
        //扫描配置类 将扫描的类加入到List。
        strings = new ArrayList<Object>();
        scan.doScan(properties.getConfiguration(), strings, true);
        //执行声明周期方法
        preInit();
        //扫描controller,加载所有的
        classes = new ArrayList<Object>();
        scan.doScan(properties.getScannerPacket(), classes, false);

        //初始化所有的 requestmapping
        ScannerRequestMapping requestMapping=new ScannerRequestMapping();
        requestMapping.doScan(classes,map);
       // logger.info(map);
        //扫描结束 启动服务器
        return properties.getPort();

    }

    private static void preInit() {
        if (strings != null && strings.size() != 0) {
            for (Object string : strings) {
                try {
                    Class<?> aClass = classLoader.loadClass((String) string);
                    LifeListern lifeListern = (LifeListern) aClass.newInstance();
                    lifeListern.preInit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            lifeListern.preInit();
        }
    }
}
