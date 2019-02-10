package club.dayuange.mypacket.session;

import java.util.*;

public class DefultSession implements HttpSession {
    private final long createTime;
    private long lastAccessedTime;
    private String id;
    private long effectiveTime;
    private Map<String, Object> attributeMap = new HashMap<>();

    public DefultSession(String id, long time) {
        this.id = id;
        Date date = new Date();
        createTime = date.getTime();
        lastAccessedTime = createTime;
        effectiveTime = time;
    }

    public long getEffectiveTime() {
        return effectiveTime;
    }


    @Override
    public long getCreationTime() {
        return createTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public Object getAttribute(String var1) {
        return attributeMap.get(var1);
    }

    @Override
    public void setAttribute(String var1, Object var2) {
        attributeMap.put(var1, var2);
    }

    @Override
    public void removeAttribute(String var1) {
        attributeMap.remove(var1);
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public String toString() {
        return "DefultSession{" +
                "createTime=" + createTime +
                ", lastAccessedTime=" + lastAccessedTime +
                ", id='" + id + '\'' +
                ", effectiveTime=" + effectiveTime +
                '}';
    }
}