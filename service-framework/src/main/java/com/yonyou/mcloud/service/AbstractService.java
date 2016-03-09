package com.yonyou.mcloud.service;

import Ice.Communicator;
import Ice.Identity;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.yonyou.mcloud.service.interceptor.ServiceDispatchInterceptor;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;

/**
 * <p>抽象IceBox服务</p>
 * <p>提供服务向ZK注册服务,并坚持服务状态</p>
 *
 * Created by hubo on 16/1/14
 */
public abstract class AbstractService implements Service {

    private ObjectAdapter adapter;

    private ServiceMonitor monitor = ServiceMonitor.getInstance();

    private Identity id;

    private boolean isMonitor = true;

    private void _start(String s, Communicator c, String[] args) {

        adapter = c.createObjectAdapter(s);

        id = c.stringToIdentity(s);

        if(isMonitor()) {
            adapter.add(ServiceDispatchInterceptor.wrapService(id, createServiceObject()), id);
        } else {
            adapter.add(createServiceObject(), id);
        }

        adapter.activate();

    }

    private void _stop() {
        adapter.deactivate();
        adapter.destroy();
    }

    public abstract Ice.Object createServiceObject();

    public abstract String getVersion();

    @Override
    public void start(String s, Communicator c, String[] args) {

        beginStart(s, c, args);
        _start(s, c, args);
        afterStart(s, c, args);
        if(isMonitor()) {
            monitor.registerService(this);
        }
    }

    @Override
    public void stop() {
        beginStop();
        _stop();
        afterStop();
        if(isMonitor()) {
            ServiceDispatchInterceptor.removeService(id);
            monitor.unregisterService(this);
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
