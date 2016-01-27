package com.yonyou.mcloud;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * Created by hubo on 16/1/15
 */
@Message
public class ServiceExecLog implements Serializable {

    public static final String TOPIC = "service_log_topic_msgpack";

    private String ip;

    private String hostName;

    private String name;

    private String op;

    private long startTime;

    private long execTime;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }


    public String toMessage() {
        return ip + "-" + hostName + "-" + name
                + "-" + op + "-" + startTime + "-" + execTime;
    }

    @Override
    public String toString() {
        return "ServiceExecLog{" +
                "ip='" + ip + '\'' +
                ", hostName='" + hostName + '\'' +
                ", name='" + name + '\'' +
                ", op='" + op + '\'' +
                ", startTime=" + startTime +
                ", execTime=" + execTime +
                '}';
    }
}
