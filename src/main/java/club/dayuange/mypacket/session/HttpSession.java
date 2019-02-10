package club.dayuange.mypacket.session;

public interface HttpSession {
    long getCreationTime();

    String getId();

    long getLastAccessedTime();

    Object getAttribute(String var1);

    void setAttribute(String var1, Object var2);


    void removeAttribute(String var1);

    long getEffectiveTime();

    boolean isNew();
}