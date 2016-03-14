package com.yonyou.mcloud.service.context;

import Ice.Identity;
import Ice.Object;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *     Service Context
 * </p>
 * Created by hubo on 16/1/14
 */
public class ServiceContext {


    public Map<Identity, Object> serviceMap = new ConcurrentHashMap<>();

    public void addService(Identity id, Object service) {
        serviceMap.put(id, service);
    }

    public Ice.Object getService(Identity key) {
        return serviceMap.get(key);
    }

    public Ice.Object removeService(Identity key) {
        return serviceMap.remove(key);
    }

}
