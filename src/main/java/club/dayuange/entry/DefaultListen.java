package club.dayuange.entry;

import club.dayuange.scanner.LifeListern;
import club.dayuange.utils.LoggerInitialization;
import org.apache.logging.log4j.Logger;

/**
 * 默认的服务器启动监听
 */
public class DefaultListen implements LifeListern {
    Logger logger=LoggerInitialization.logger;
    public void preInit() {
        logger.info("the server start init all requestmapping");
    }

    public void afterInit() {
        logger.info("the server closed!");

    }
}