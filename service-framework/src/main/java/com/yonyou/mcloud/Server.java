package com.yonyou.mcloud;

import Ice.InitializationData;
import Ice.Util;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;

/**
 * zeroc ice 服务器
 * Created by hubo on 16/1/26
 */
public class Server {

    public static void main(String[] args) {
        InitializationData initData = new InitializationData();
        initData.properties = Util.createProperties();
        initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
        IceBox.Server s = new IceBox.Server();
        int status = s.main("service-center", args, initData);
        ServiceMonitor.getInstance().close();
        System.exit(status);
    }

}
