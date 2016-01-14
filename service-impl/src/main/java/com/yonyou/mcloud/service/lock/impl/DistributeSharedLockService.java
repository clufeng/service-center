package com.yonyou.mcloud.service.lock.impl;

import Ice.Object;
import com.yonyou.mcloud.service.AbstractService;

/**
 * Created by duduchao on 16/1/14
 */
public class DistributeSharedLockService extends AbstractService {

    private DistributedSharedLockImpl lockImpl;

    @Override
    public Object createServiceObject() {
        lockImpl = new DistributedSharedLockImpl();
        return lockImpl;
    }

    @Override
    public String getIdentityStr() {
        return "LockServiceAdapter";
    }

    @Override
    public void afterStop() {
        super.afterStop();
        lockImpl.destroy();
    }
}
