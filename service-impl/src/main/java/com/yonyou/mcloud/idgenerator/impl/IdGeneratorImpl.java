package com.yonyou.mcloud.idgenerator.impl;

import Ice.Current;
import com.yonyou.mcloud.idgenerator._IdGeneratorDisp;
import com.yonyou.mcloud.utils.DistributedIdGenerator;
import com.yonyou.mcloud.utils.MemcacheIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式主键生成器
 * Created by hubo on 16/1/14
 */
public class IdGeneratorImpl extends _IdGeneratorDisp {

    private static final Logger logger = LoggerFactory.getLogger(IdGeneratorImpl.class);

    private DistributedIdGenerator idGenerator;

    public IdGeneratorImpl() {
        idGenerator = new MemcacheIdGenerator();
    }

    @Override
    public String nextIdByModule(String moduleCode, Current __current) {
        logger.info("nextIdByModule : module[{}]", moduleCode);
        return idGenerator.nextId(moduleCode);
    }

    @Override
    public String nextId(Current __current) {
        logger.info("nextId");
        return idGenerator.nextId();
    }

}
