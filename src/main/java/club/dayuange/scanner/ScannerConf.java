package club.dayuange.scanner;

import club.dayuange.annotation.CoreDeal;
import club.dayuange.annotation.FilterCnf;
import club.dayuange.entry.SimpleProperties;
import club.dayuange.utils.LoggerInitialization;
import club.dayuange.utils.StringUtils;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * 扫描配置文件
 */
public class ScannerConf {

    private final ClassLoader cl;
    private Integer p;
    private boolean logo = true;
    private Logger logger = LoggerInitialization.logger;

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
                logger.warn(e + "，port use default 80");
                p = 80;
            }
            try {
                this.logo = StringUtils.String2Bool(logo);
            } catch (Exception e) {
                logger.warn(e + "，logo use default true");
                this.logo = true;
            }
            SimpleProperties properties = new SimpleProperties(p, scannerpacket, configuration, this.logo);

            return properties;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }


    public void doScan(final String basePackage, List<Class> ordClass, List<Class> annotationList) throws IOException, ClassNotFoundException {
        if (basePackage == null) return;
        final String splashPath = StringUtils.dotToSplash(basePackage);
        final URL url = cl.getResource(splashPath);
        String filePath = StringUtils.getRootPath(url);
        List<String> list;
        list = new ArrayList<String>();
        if (url == null)
            return;
        if (isJarFile(filePath)) {
            // jar file
            if (logger.isDebugEnabled()) {
                logger.debug("{} is a jar", filePath);
            }
            list = readFromJarFile(filePath, splashPath);
        } else {
            // directory
            if (logger.isDebugEnabled()) {
                logger.debug("{} is a directory", filePath);
            }
            //获取目录下所有的文件
            list.addAll(readFromDirectory(filePath));
        }
        for (String name : list) {
            //是不是一个class
            if (isClassFile(name)) {
                String clazz = toFullyQualifiedName(name, basePackage);
                //   System.out.println(clazz);
                Class<?> aClass = cl.loadClass(clazz);
                Annotation[] annotations = aClass.getAnnotations();
                if (annotations.length>0) {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof CoreDeal) {
                            annotationList.add(aClass);
                            break;
                        }
                        if (annotation instanceof FilterCnf) {
                            annotationList.add(aClass);
                            break;
                        }
                    }
                } else {
                    if (ordClass != null)
                        ordClass.add(aClass);
                }

            } else {
                doScan(basePackage + "." + name, ordClass, annotationList);
            }
        }
        if (logger.isDebugEnabled()) {
            if (ordClass != null) {
                for (Object n : ordClass) {
                    logger.debug("find {}", n);
                }
            }
            for (Object n : annotationList) {
                logger.debug("find {}", n);
            }
        }
    }


    private static List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();
        if (null == names) {
            return null;
        }
        return Arrays.asList(names);
    }

    private static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private static boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    private static String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtils.trimExtension(shortName));
        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("read to jar: {}", jarPath);
        }
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        List<String> nameList = new ArrayList<String>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }
            entry = jarIn.getNextJarEntry();
        }
        return nameList;
    }
}