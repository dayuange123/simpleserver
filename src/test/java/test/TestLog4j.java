package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestLog4j {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Test
    public void test01() {
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");

    }
    @Test
    public void test02() {
        StringBuilder sb=new StringBuilder();
        sb.append("server start\n");
        sb.append("    -       -     ---     \n");
        sb.append("  -----    --  ---------    \n");
        sb.append("   - -      -     -   -         Server    \n");
        sb.append("  -   -    -    -     -    \n");
        sb.append(" -     -   ---------------     \n");
        logger.info(sb.toString());
    }

}