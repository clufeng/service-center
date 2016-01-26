package com.yonyou.mcloud.registry;

import com.yonyou.mcloud.RegistryMeta;

import java.util.List;

/**
 * Created by hubo on 16/1/26
 */
public interface NotifyListener {
    void notify(List<RegistryMeta> metas);
}
