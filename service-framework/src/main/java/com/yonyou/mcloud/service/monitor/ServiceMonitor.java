package com.yonyou.mcloud.service.monitor;

import com.yonyou.mcloud.service.logger.ServiceExecLog;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hubo on 16/1/14
 */
public class ServiceMonitor {

    public static final String LOG_TOPIC = "service_log_topic";

    private static final String service_zk_node_path="/mcloud/service";

    private volatile static ServiceMonitor monitor;

    private Producer<String, ServiceExecLog> logProducer;

    private static ExecutorService exec;

    private void initLogProducer() {
        Properties props = new Properties();

        ResourceBundle resourceBundle = ResourceBundle.getBundle("logproducer");

        Enumeration<String> enumeration = resourceBundle.getKeys();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            props.put(key, resourceBundle.getString(key));
        }

        logProducer = new Producer<>(new ProducerConfig(props));
    }

    private ServiceMonitor() {
        exec = Executors.newCachedThreadPool();
        initLogProducer();
    }

    public static ServiceMonitor getInstance() {
        if(monitor == null) {
            synchronized (ServiceMonitor.class) {
                if(monitor == null) {
                    monitor = new ServiceMonitor();
                }
            }
        }

        return monitor;
    }

    public void recordServiceExecLogAsyn(final String name, final String op, final long startTime, final long execTime) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                String addr;

                String hostName;

                try {
                    addr = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    addr = "0.0.0.0";
                }

                try {
                    hostName = InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    hostName="unknown";
                }

                ServiceExecLog log = new ServiceExecLog();
                log.setName(name);
                log.setOp(op);
                log.setExecTime(execTime);
                log.setStartTime(startTime);
                log.setIp(addr);
                log.setHostName(hostName);

                String messageKey = startTime +":"+ name + ":" + addr;

                logProducer.send(new KeyedMessage<>(LOG_TOPIC, messageKey, log));
            }
        });
    }



}
