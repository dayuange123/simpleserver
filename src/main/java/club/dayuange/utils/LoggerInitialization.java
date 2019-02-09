package club.dayuange.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerInitialization {
    public final static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static void printLogo() {
        StringBuilder sb=new StringBuilder();
        sb.append("server start\n");
        sb.append("     -      -     ---     \n");
        sb.append(" -------   --  ---------    \n");
        sb.append("   - -      -     -   -         Server    \n");
        sb.append("  -   -    -    -      -    \n");
        sb.append(" -     -  -----------------     \n");
        logger.info(sb.toString());
    }

}