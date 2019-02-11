package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

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
        StringBuilder sb = new StringBuilder();
        sb.append("server start\n");
        sb.append("    -       -     ---     \n");
        sb.append("  -----    --  ---------    \n");
        sb.append("   - -      -     -   -         Server    \n");
        sb.append("  -   -    -    -     -    \n");
        sb.append(" -     -   ---------------     \n");
        logger.info(sb.toString());
    }

    public void method1(String name, String email, int a) {
        System.out.println(name + ":" + email);

    }

    @Test
    public void test() {
        Class<TestLog4j> clazz = TestLog4j.class;
        try {
            //得到方法实体
            Method method = clazz.getMethod("method1", String.class, String.class, int.class);
            //得到该方法参数信息数组
            Parameter[] parameters = method.getParameters();
            //遍历参数数组，依次输出参数名和参数类型
            Arrays.stream(parameters).forEach(p -> {
                System.out.println(p.getName() + " : " + p.getType());
                System.out.println(p.getType().isPrimitive());
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}