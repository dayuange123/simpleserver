package club.dayuange.corecontainer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存的静态的html文件
 */
public class CacheStaticHtml {
    private static Map<String, byte[]> map = new ConcurrentHashMap<String, byte[]>();

    public static byte[] getBytes(String path) {
        return map.get(path);
    }
    public static void put(String path,byte[] bytes){
        if(!map.containsKey(path)){
            map.put(path,bytes);
        }
    }
}