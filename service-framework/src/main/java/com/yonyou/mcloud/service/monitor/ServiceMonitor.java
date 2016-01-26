package com.yonyou.mcloud.service.monitor;

import Ice.Communicator;
import IceGrid.*;
import com.yonyou.mcloud.RegistryMeta;
import com.yonyou.mcloud.registry.RegistryAgent;
import com.yonyou.mcloud.registry.impl.zk.ZookeeperRegistryAgent;
import com.yonyou.mcloud.service.AbstractService;
import com.yonyou.mcloud.service.logger.ServiceExecLog;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service 监控
 * Created by hubo on 16/1/14
 */
public class ServiceMonitor {

    public static final String LOG_TOPIC = "service_log_topic_msgpack";

    private static final String service_zk_node_path="/mcloud/service/";

    private static final Logger log = LoggerFactory.getLogger("moniter");

    private volatile static ServiceMonitor monitor;

    private static ExecutorService exec;

    private Producer<String, ServiceExecLog> logProducer;

    private RegistryAgent agent;

    private String hostAddr;

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
        agent = new ZookeeperRegistryAgent(service_zk_node_path);
        try {
            hostAddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("unknown host");
            hostAddr = "0.0.0.0";
        }
        initLogProducer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                close();
            }
        }));
    }

    public synchronized void registerService(AbstractService service) {
        RegistryMeta meta = new RegistryMeta(hostAddr, service.getClass().getName(), service.getVersion());

        if (agent.isRegistered(meta)) {
            log.warn("服务[{}]已注册!", meta);
            return;
        }
        agent.register(meta);
    }

    public synchronized void unregisterService(AbstractService service) {
        RegistryMeta meta = new RegistryMeta(hostAddr, service.getClass().getName(), service.getVersion());
        if(agent.isRegistered(meta)) {
            agent.unregister(meta);
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
        agent.close();
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


    public static void main(String[] args) throws PermissionDeniedException {
        Communicator ic = Ice.Util.initialize(new String[]{"--Ice.Default.Locator=IceGrid/Locator:tcp -h 192.168.20.17 -p 4061:tcp -h 192.168.20.18 -p 4061"});

        RegistryPrx r = RegistryPrxHelper.checkedCast(ic.stringToProxy("IceGrid/Registry"));

        AdminSessionPrx sessionPrx = r.createAdminSession("test","test");

        AdminPrx admin = sessionPrx.getAdmin();

        System.out.println(admin.getObjectInfosByType(null).length);

        System.out.println(Arrays.toString(admin.getAllAdapterIds()));

        sessionPrx.destroy();

        ic.shutdown();
        ic.destroy();
    }



}
