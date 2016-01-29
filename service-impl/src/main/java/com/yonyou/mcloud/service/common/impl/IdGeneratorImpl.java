package com.yonyou.mcloud.service.common.impl;

import Ice.Current;
import com.yonyou.mcloud.service.common._IdGeneratorDisp;
import com.yonyou.mcloud.utils.DistributedIdGenerator;
import com.yonyou.mcloud.utils.MemcacheIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式主键生成器
 * Created by hubo on 16/1/14
 */
public class IdGeneratorImpl extends _IdGeneratorDisp{

    private static final Logger log = LoggerFactory.getLogger(IdGeneratorImpl.class);

    private DistributedIdGenerator idGenerator;

    public IdGeneratorImpl() {
        idGenerator = new MemcacheIdGenerator();
    }

    @Override
    public String nextId(Current __current) {
        return idGenerator.nextId();
    }

}
