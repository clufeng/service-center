package com.yonyou.mcloud.registry.impl.zk;

import com.yonyou.mcloud.RegistryMeta;
import com.yonyou.mcloud.registry.AbstractRegistryAgent;
import com.yonyou.mcloud.zookeeper.StateListener;
import com.yonyou.mcloud.zookeeper.ZookeeperClient;
import com.yonyou.mcloud.zookeeper.impl.CuratorZookeeperClient;

/**
 * Created by duduchao on 16/1/26
 */
public class ZookeeperRegistryAgent extends AbstractRegistryAgent{

    public static final String DEFAULT_ROOT = "/service-center/";

    private String root;

    private ZookeeperClient zkClient = null;

    public ZookeeperRegistryAgent() {
        this(DEFAULT_ROOT);
    }

    public ZookeeperRegistryAgent(String root) {
        this.root = root;
        this.zkClient = new CuratorZookeeperClient();
        this.zkClient.addStateListener(new StateListener() {
            @Override
            public void stateChanged(int state) {
                if(state == RECONNECTED) {
                    recover();
                }
            }
        });
    }

    @Override
    protected void doRegister(RegistryMeta meta) {
        zkClient.create(root + meta.toPath(), true);
    }

    @Override
    protected void doUnregister(RegistryMeta meta) {
        zkClient.delete(root  + meta.toPath());
    }

    @Override
    public void close() {
        super.close();
        zkClient.close();
    }

}
