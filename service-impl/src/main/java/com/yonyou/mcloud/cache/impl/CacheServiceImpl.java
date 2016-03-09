package com.yonyou.mcloud.cache.impl;

import Ice.Current;
import com.yonyou.mcloud.cache.CacheException;
import com.yonyou.mcloud.cache._CacheServiceDisp;
import com.yonyou.mcloud.memcached.MemcachedUtils;

import java.io.Serializable;

/**
 * 缓存服务实现
 * Created by hubo on 2016/3/9
 */
public class CacheServiceImpl extends _CacheServiceDisp {

    @Override
    public Serializable get(String key, Current __current) throws CacheException {
        return MemcachedUtils.get(key);
    }

    @Override
    public boolean add(String key, Serializable value, int expire, Current __current) throws CacheException {
        return MemcachedUtils.add(key, value, expire);
    }

    @Override
    public boolean set(String key, Serializable value, int expire, Current __current) throws CacheException {
        return MemcachedUtils.set(key, value, expire);
    }

    @Override
    public boolean evict(String key, Current __current) throws CacheException {
        return MemcachedUtils.remove(key);
    }
}
