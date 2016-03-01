package com.yonyou.mcloud;

import Ice.InitializationData;
import Ice.Util;
import com.yonyou.mcloud.service.logger.SystemLogger;

/**
 * zeroc ice 服务器
 * Created by hubo on 16/1/26
 */
public class Server {

    public static final String SYS_LOG_NAME = "syslog";

    public static void main(String[] args) {
        InitializationData initData = new InitializationData();
        initData.properties = Util.createProperties();
        initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
        initData.logger = new SystemLogger(SYS_LOG_NAME);
        Util.setProcessLogger(new SystemLogger(SYS_LOG_NAME));

        IceBox.Server s = new IceBox.Server();

        int status = s.main("service-center", args, initData);

        System.exit(status);
    }

}
