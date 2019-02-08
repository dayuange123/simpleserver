package club.dayuange.scanner;

import club.dayuange.entry.SimpleProperties;
import club.dayuange.utils.LoggerInitialization;
import club.dayuange.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * 扫描配置文件
 */
public class ScannerConf {

    private final ClassLoader cl;
    private Integer p;
    private boolean logo = true;
    private Logger logger= LoggerInitialization.logger;
    public ScannerConf(ClassLoader cl) {
        this.cl = cl;
    }

    public SimpleProperties loadProperties() {
        try {
            Properties prop = new Properties();
            prop.load(cl.getResourceAsStream("simpleserver.properties"));
            String port = prop.getProperty("port");
            String scannerpacket = prop.getProperty("scannerpacket");
            String configuration = prop.getProperty("configuration");
            String logo = prop.getProperty("logo");
            try {
                p = StringUtils.String2Int(port);
            } catch (Exception e) {
                logger.error(e);
                p = 80;
            }
            try {
                this.logo = StringUtils.String2Bool(logo);
            } catch (Exception e) {
                logger.error(e);
                this.logo = true;
            }
            SimpleProperties properties = new SimpleProperties(p, scannerpacket, configuration, this.logo);

            return properties;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}