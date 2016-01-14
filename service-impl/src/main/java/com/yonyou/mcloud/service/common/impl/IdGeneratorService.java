package com.yonyou.mcloud.service.common.impl;

import Ice.Object;
import com.yonyou.mcloud.service.AbstractService;

/**
 * Created by duduchao on 16/1/14
 */
public class IdGeneratorService extends AbstractService {
    @Override
    public Object createServiceObject() {
        return new IdGeneratorImpl();
    }

    @Override
    public String getIdentityStr() {
        return "IdGeneratorServiceAdapter";
    }
}
