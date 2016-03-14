package com.yonyou.mcloud.service.monitor;

import com.yonyou.mcloud.RegistryMeta;
import com.yonyou.mcloud.ServiceExecLog;
import com.yonyou.mcloud.kafka.KafkaProducer;
import com.yonyou.mcloud.registry.RegistryAgent;
import com.yonyou.mcloud.registry.impl.zk.ZookeeperRegistryAgent;
import com.yonyou.mcloud.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service 监控
 * Created by hubo on 16/1/14
 */
public class ServiceMonitor {

    private static final String SERVICE_ZK_NODE_PATH="/mcloud/service/";

    private static final Logger log = LoggerFactory.getLogger("moniter");

    private volatile static ServiceMonitor monitor;

    private static ExecutorService exec;

    private KafkaProducer<ServiceExecLog> logProducer;

    private RegistryAgent agent;

    private String hostAddr;
    private String hostName;

    private ServiceMonitor() {

        exec = Executors.newCachedThreadPool();
        agent = new ZookeeperRegistryAgent(SERVICE_ZK_NODE_PATH);

        try {
            hostAddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("unknown host");
            hostAddr = "0.0.0.0";
        }

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.warn("unknown host name");
            hostName="unknown";
        }

        logProducer = new KafkaProducer<>(ServiceExecLog.TOPIC);

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
                ServiceExecLog log = new ServiceExecLog();
                log.setName(name);
                log.setOp(op);
                log.setExecTime(execTime);
                log.setStartTime(startTime);
                log.setIp(hostAddr);
                log.setHostName(hostName);
                logProducer.produce(log);
            }
        });
    }

}
