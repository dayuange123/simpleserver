package club.dayuange.scanner;

import club.dayuange.entry.SimpleProperties;
import club.dayuange.exection.CheckExection;
import club.dayuange.utils.CheckAccess;
import club.dayuange.utils.LoggerInitialization;
import org.apache.logging.log4j.Logger;

/**
 * 主扫描启动，主要一些生命周期方法
 */
public class MainStartup {

    private static SimpleProperties properties = null;
    private static Logger logger = LoggerInitialization.logger;

    public static void init(ClassLoader c) {
        //首先加载配置类
        ScannerConf scan = new ScannerConf(c);
        properties = scan.loadProperties();
        try {
            CheckAccess.checkNull(properties);
        } catch (CheckExection checkExection) {
            logger.error(checkExection.getMessage());
        }





    }

}