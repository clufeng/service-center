package com.yonyou.mcloud;

/**
 * Created by hubo on 16/1/26
 */
public class RegistryMeta {

    public static final String DEFLAUT_VERSION = "1.0.0";

    public static final String UNKOWN_ADDRESS = "0.0.0.0";

    private final String version;

    private final String address;

    private final String service;

    public RegistryMeta(String address, String service, String version) {
        this.address = address == null ? UNKOWN_ADDRESS : address;
        this.service = service;
        this.version = version == null ? DEFLAUT_VERSION : version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistryMeta that = (RegistryMeta) o;

        if (!version.equals(that.version)) return false;
        if (!address.equals(that.address)) return false;
        return service.equals(that.service);

    }

    @Override
    public int hashCode() {
        int result = version.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + service.hashCode();
        return result;
    }

    public String getVersion() {
        return version;
    }

    public String getAddress() {
        return address;
    }


    public String getService() {
        return service;
    }

    @Override
    public String toString() {
        return "RegistryMeta{" +
                "address='" + address + '\'' +
                ", version='" + version + '\'' +
                ", service='" + service + '\'' +
                '}';
    }

    public String toPath() {
        return getService() + "/" + getVersion() + "/" + getAddress();
    }

}
