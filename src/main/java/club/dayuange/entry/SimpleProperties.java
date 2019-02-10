package club.dayuange.entry;

/**
 * 配置类的实体
 */
public class SimpleProperties {

    private final Integer port;
    private final String scannerPacket;
    private final String configuration;
    private final boolean logo;

    public SimpleProperties(Integer port, String scannerPacket, String configuration, boolean logo) {
        this.port = port;
        this.scannerPacket = scannerPacket;
        this.configuration = configuration;
        this.logo = logo;
    }

    public Integer getPort() {
        return port;
    }

    public String getScannerPacket() {
        return scannerPacket;
    }

    public String getConfiguration() {
        return configuration;
    }

    public boolean isLogo() {
        return logo;
    }

    @Override
    public String toString() {
        return "SimpleProperties{" +
                "port=" + port +
                ", scannerPacket='" + scannerPacket + '\'' +
                ", configuration='" + configuration + '\'' +
                ", logo=" + logo +
                '}';
    }
}