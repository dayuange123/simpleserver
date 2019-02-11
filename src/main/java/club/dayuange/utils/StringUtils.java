package club.dayuange.utils;

import java.net.URL;

public class StringUtils {
    public static Integer String2Int(String s) {
        return Integer.valueOf(s);
    }

    public static Boolean String2Bool(String s) {
        return Boolean.valueOf(s);
    }

    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }
        return fileUrl.substring(5, pos);
    }

    public static String dotToSplash(String basePackage) {
        return basePackage.replaceAll("\\.", "/");
    }

    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    /**
     * /application/home -> /home
     *
     * @param uri
     * @return
     */
    public static String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');

        return trimmed.substring(splashIndex);
    }

    public static String removeIfStartWith(String s, String start) {
        if (s.startsWith(start)) {
            return s.substring(start.length());
        }
        return s;
    }

    public static String replaceD2comma(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                stringBuilder.append(",");
            } else stringBuilder.append(s.charAt(i));
        }
        return stringBuilder.toString();
    }

    public static boolean isPacketType(Class object) {
        if (object.isAssignableFrom(Byte.class) || object.isAssignableFrom(Character.class)
                || object.isAssignableFrom(Boolean.class) || object.isAssignableFrom(Short.class)
                || object.isAssignableFrom(Integer.class) || object.isAssignableFrom(Long.class)
                || object.isAssignableFrom(Float.class) || object.isAssignableFrom(Double.class) || object.isAssignableFrom(String.class)) {
            return true;
        }
        return false;
    }

    public static Object getDefultValue(Class aClass) {
        if (aClass.isAssignableFrom(int.class)) {
            return 0;
        } else if (aClass.isAssignableFrom(boolean.class)) {
            return false;
        } else if (aClass.isAssignableFrom(byte.class)) {
            return (byte) 0;
        } else if (aClass.isAssignableFrom(short.class)) {
            return (short) 0;
        } else if (aClass.isAssignableFrom(long.class)) {
            return 0l;
        } else if (aClass.isAssignableFrom(float.class)) {
            return 0.0f;
        } else {
            return 0.0d;
        }
    }
}