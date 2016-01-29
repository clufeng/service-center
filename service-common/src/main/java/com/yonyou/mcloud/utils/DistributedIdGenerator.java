package com.yonyou.mcloud.utils;

/**
 * Id主键生成器
 * Created by hubo on 16/1/29
 */
public interface DistributedIdGenerator {

    String nextId();

    String nextId(String module);

}
