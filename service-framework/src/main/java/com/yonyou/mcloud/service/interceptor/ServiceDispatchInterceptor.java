package com.yonyou.mcloud.service.interceptor;

import Ice.*;
import Ice.Object;
import com.yonyou.mcloud.service.context.ServiceContext;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;

/**
 * <p>Service调用Interceptor</p>
 * Created by hubo on 16/1/14
 */
public class ServiceDispatchInterceptor extends DispatchInterceptor{

    private static ServiceContext context = new ServiceContext();

    private static ServiceDispatchInterceptor interceptor = new ServiceDispatchInterceptor();

    private ServiceDispatchInterceptor() {
    }

    public static DispatchInterceptor wrapService(Identity id, Object service) {
        context.addService(id, service);
        return interceptor;
    }

    public static DispatchInterceptor removeService(Identity id) {
        context.removeService(id);
        return interceptor;
    }

    @Override
    public DispatchStatus dispatch(Request request) {

        Object serviceObj = context.getService(request.getCurrent().id);

        final long startTime = System.currentTimeMillis();

        DispatchStatus result = serviceObj.ice_dispatch(request);

        final long execMillisTime = System.currentTimeMillis() - startTime;

        if(!request.getCurrent().operation.equals("ice_isA")) {
            ServiceMonitor.getInstance().recordServiceExecLogAsyn(
                    request.getCurrent().id.name, request.getCurrent().operation, startTime, execMillisTime);
        }

        return result;
    }


}
