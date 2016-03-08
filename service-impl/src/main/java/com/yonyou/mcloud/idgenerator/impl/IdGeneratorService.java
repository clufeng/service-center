package com.yonyou.mcloud.idgenerator.impl;

import Ice.Object;
import com.yonyou.mcloud.service.AbstractService;

/**
 * Created by hubo on 16/1/14
 */
public class IdGeneratorService extends AbstractService {
    @Override
    public Object createServiceObject() {
        return new IdGeneratorImpl();
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
