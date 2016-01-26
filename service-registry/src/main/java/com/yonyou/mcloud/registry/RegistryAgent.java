package com.yonyou.mcloud.registry;

import com.yonyou.mcloud.RegistryMeta;

/**
 * Created by hubo on 16/1/26
 */
public interface RegistryAgent {

    void register(RegistryMeta meta);

    void unregister(RegistryMeta meta);

    void close();

    boolean isRegistered(RegistryMeta meta);

//    void subscribe(RegistryMeta meta, NotifyListener listener);
//
//    void unsubscribe(RegistryMeta meta, NotifyListener listener);
//
//    List<RegistryMeta> lookup(RegistryMeta meta);

}
