package com.yonyou.mcloud.service.interceptor;

import Ice.*;
import Ice.Object;
import com.yonyou.mcloud.service.context.ServiceContext;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;

/**
 * Created by hubo on 16/1/14
 */
public class ServiceDispatchInterceptor extends DispatchInterceptor{

    private static ServiceContext context = new ServiceContext();

    public ServiceDispatchInterceptor(Identity id, Object service) {
        context.addService(id, service);
    }

    @Override
    public DispatchStatus dispatch(Request request) {

        Object serviceObj = context.getServiceMap().get(request.getCurrent().id);

        final String op = request.getCurrent().operation;

        final String id = request.getCurrent().id.name;

        final long startTime = System.currentTimeMillis();

        DispatchStatus result = serviceObj.ice_dispatch(request);

        final long execMillisTime = System.currentTimeMillis() - startTime;

        if(!op.equals("ice_isA")) {
            ServiceMonitor.getInstance().recordServiceExecLogAsyn(id, op, startTime, execMillisTime);
        }

        return result;
    }


}
