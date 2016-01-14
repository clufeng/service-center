package com.yonyou.mcloud.service.interceptor;

import Ice.*;
import Ice.Object;
import com.yonyou.mcloud.service.context.ServiceContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hubo on 16/1/14
 */
public class ServiceDispatchInterceptor extends DispatchInterceptor{

    private static ServiceContext context = new ServiceContext();

    private static ExecutorService exec = Executors.newCachedThreadPool();

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

        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(startTime + "--" + id + ":" + op + "--" + execMillisTime);
            }
        });

        return result;
    }


}
