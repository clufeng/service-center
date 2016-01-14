package com.yonyou.mcloud.service.context;

import Ice.Identity;
import Ice.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duduchao on 16/1/14
 */
public class ServiceContext {


    public Map<Identity, Object> serviceMap = new HashMap<>();

    public Map<Identity, Object> getServiceMap() {
        return serviceMap;
    }

    public void addService(Identity id, Object service) {
        serviceMap.put(id, service);
    }

}
