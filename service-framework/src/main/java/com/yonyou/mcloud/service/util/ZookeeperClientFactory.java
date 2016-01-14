package com.yonyou.mcloud.service.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by duduchao on 16/1/14
 */
public class ZookeeperClientFactory {

    public static final String CONNECT_STR= "connect_str";

    public static final String BASE_SLEEP_TIMEMS = "base_sleep_timems";

    public static final String MAX_RETRIES = "max_retries";

    private static final Map<String, String> config;

    static {
        config = new HashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("zk");

        Enumeration<String> enumeration = resourceBundle.getKeys();

        while (enumeration.hasMoreElements()) {
           String key = enumeration.nextElement();
            config.put(key, resourceBundle.getString(key));
        }
    }

    public static CuratorFramework create() {
        return CuratorFrameworkFactory.
                newClient(config.get(CONNECT_STR), new ExponentialBackoffRetry(Integer.valueOf(config.get(BASE_SLEEP_TIMEMS)),
                        Integer.valueOf(config.get(MAX_RETRIES))));
    }

}
