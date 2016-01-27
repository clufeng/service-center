package com.yonyou.mcloud.zookeeper.impl;

import com.yonyou.mcloud.utils.PropertiesUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Map;

/**
 * Created by hubo on 16/1/14
 */
public class CuratorZookeeperClientFactory {

    public static final String CONNECT_STR= "connect_str";

    public static final String BASE_SLEEP_TIMEMS = "base_sleep_timems";

    public static final String MAX_RETRIES = "max_retries";

    private static final Map<String, String> config;

    static {
        config = PropertiesUtil.createMapByProperties("zk");
    }

    public static CuratorFramework create() {
        return CuratorFrameworkFactory.
                newClient(config.get(CONNECT_STR), new ExponentialBackoffRetry(Integer.valueOf(config.get(BASE_SLEEP_TIMEMS)),
                        Integer.valueOf(config.get(MAX_RETRIES))));
    }

}
