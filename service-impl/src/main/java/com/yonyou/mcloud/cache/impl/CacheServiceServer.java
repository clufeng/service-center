package com.yonyou.mcloud.cache.impl;

import Ice.Object;
import com.yonyou.mcloud.service.AbstractService;

/**
 * Created by hubo on 2016/3/9
 */
public class CacheServiceServer extends AbstractService{
    @Override
    public Object createServiceObject() {
        return new CacheServiceImpl();
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
