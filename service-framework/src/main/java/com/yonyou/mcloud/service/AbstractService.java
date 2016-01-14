package com.yonyou.mcloud.service;

import Ice.Communicator;
import Ice.Identity;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.yonyou.mcloud.service.interceptor.ServiceDispatchInterceptor;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;
import com.yonyou.mcloud.service.util.ZookeeperClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;

/**
 * <p>抽象IceBox服务</p>
 * <p>提供服务向ZK注册服务,并坚持服务状态</p>
 *
 * Created by hubo on 16/1/14
 */
public abstract class AbstractService implements Service {

    private CuratorFramework client;

    private ObjectAdapter adapter;

    private ServiceMonitor serviceMonitor;

    private boolean isMonitor = true;

    private void _start(String s, Communicator c, String[] args) {

        adapter = c.createObjectAdapter(s);

        Identity id = c.stringToIdentity(s);

        if(isMonitor()) {
            adapter.add(new ServiceDispatchInterceptor(id, createServiceObject()), id);
        } else {
            adapter.add(createServiceObject(), id);
        }

        adapter.activate();

    }

    private void _stop() {
        adapter.destroy();
    }

    public abstract Ice.Object createServiceObject();

    public abstract String getIdentityStr();

    @Override
    public void start(String s, Communicator c, String[] args) {

        client = ZookeeperClientFactory.create();
        client.start();

        beginStart(s, c, args);
        _start(s, c, args);
        afterStart(s, c, args);

        try {

            String hostAddr = InetAddress.getLocalHost().getHostAddress();

            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/mcloud/service/", (getIdentityStr()
                    + ":" + this.getClass().getName() + ":" + hostAddr).getBytes());

        } catch (Exception e) {
            e.printStackTrace();
            client.close();
        }
    }

    @Override
    public void stop() {

        beginStop();
        _stop();
        afterStop();

        if(client != null) {
            client.close();
        }

    }

    protected void beginStart(String s, Communicator c, String[] args) {}

    protected void afterStart(String s, Communicator c, String[] args) {}

    protected void beginStop() {}

    protected void afterStop() {}

    public boolean isMonitor() {
        return isMonitor;
    }

    public void setMonitor(boolean monitor) {
        isMonitor = monitor;
    }

}
