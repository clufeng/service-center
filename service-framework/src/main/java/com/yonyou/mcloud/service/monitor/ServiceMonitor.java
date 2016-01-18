package com.yonyou.mcloud.service.monitor;

import Ice.Identity;
import com.yonyou.mcloud.service.AbstractService;
import com.yonyou.mcloud.service.logger.ServiceExecLog;
import com.yonyou.mcloud.service.util.ZookeeperClientFactory;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service 监控
 * Created by hubo on 16/1/14
 */
public class ServiceMonitor {

    public static final String LOG_TOPIC = "service_log_topic";

    private static final String service_zk_node_path="/mcloud/service/";

    private static final Logger log = LoggerFactory.getLogger("moniter");

    private volatile static ServiceMonitor monitor;

    private static ExecutorService exec;

    private Producer<String, ServiceExecLog> logProducer;

    private Map<Identity, CuratorFramework> zkClientMap;

    private void initLogProducer() {
        Properties props = new Properties();

        ResourceBundle resourceBundle = ResourceBundle.getBundle("logproducer");

        Enumeration<String> enumeration = resourceBundle.getKeys();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            props.put(key, resourceBundle.getString(key));
        }

        logProducer = new Producer<>(new ProducerConfig(props));

        zkClientMap = new ConcurrentHashMap<>();
    }

    private ServiceMonitor() {
        exec = Executors.newCachedThreadPool();
        initLogProducer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                close();
            }
        }));
    }

    public synchronized void registerService(Identity id, AbstractService service) {

        if(zkClientMap.containsKey(id)) {
            log.warn("服务[{}]注册!", id.name);
            return;
        }

        CuratorFramework zkClient = ZookeeperClientFactory.create();
        zkClient.start();

        try {
            String hostAddr = InetAddress.getLocalHost().getHostAddress();
            zkClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(service_zk_node_path, (id.name
                            + ":" + service.getClass().getName() + ":" + hostAddr).getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            zkClient.close();
        }

        zkClientMap.put(id, zkClient);
    }

    public synchronized void unregisterService(Identity id) {
        CuratorFramework zkClient = zkClientMap.get(id);
        if(zkClient != null) {
            zkClient.close();
        }
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

    public void close() {
        logProducer.close();

        if(zkClientMap.size() > 0) {
            for (CuratorFramework zkClient : zkClientMap.values()) {
                if(zkClient != null) {
                    zkClient.close();
                }
            }
        }

        zkClientMap.clear();

        exec.shutdown();
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

                logProducer.send(new KeyedMessage<String, ServiceExecLog>(LOG_TOPIC, log));
            }
        });
    }



}
