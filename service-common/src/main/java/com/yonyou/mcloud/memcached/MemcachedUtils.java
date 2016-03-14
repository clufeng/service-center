package com.yonyou.mcloud.memcached;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

/**
 * Created by hubo on 2016/1/4
 */
public class MemcachedUtils {

    private static Logger logger = LoggerFactory.getLogger(MemcachedUtils.class);

    private static MemcachedClient memcachedClient;

    /**
     * 不允许创建实例
     */
    private MemcachedUtils() {

    }

    static {
        // 获取memcached服务器列表

        String servers = ResourceBundle.getBundle("memcached").getString("servers");

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
        // 使用二进制协议
        builder.setCommandFactory(new BinaryCommandFactory());
        try {
            memcachedClient = builder.build();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(-1);
        }
    }

    /**
     * 向缓存添加键值对并为该键值对设定逾期时间（即多长时间后该键值对从Memcached内存缓存中删除，比如： new Date(1000*10)，则表示十秒之后从Memcached内存缓存中删除）。
     */
    public static boolean add(String key, Object value, int expire) {
        try {
            return memcachedClient.add(key, expire, value);
        } catch (Exception e) {
            logger.error("key : {}, value : {}, expire : {}", key, value, expire);
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static boolean set(String key, Object value, int expire) {
        try {
            return memcachedClient.set(key, expire, value);
        } catch (Exception e) {
            logger.error("key : {}, value : {}, expire : {}", key, value, expire);
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据键获取Memcached内存缓存管理系统中相应的值
     */
    public static <T extends Serializable> T get(String key) {
        try {
            return memcachedClient.get(key);
        } catch (Exception e) {
            logger.error("key : {}", key);
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static boolean remove(String key) {
        try {
            return memcachedClient.delete(key);
        } catch (Exception e) {
            logger.error("key : {}", key);
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public static void destory() throws IOException {
        memcachedClient.shutdown();
    }


    public static long incr(String key, long delta, int initValue) {
        try {
            return memcachedClient.incr(key, delta, initValue);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            logger.error("key : {}, delta : {}, initValue : {}", key, delta, initValue);
            logger.error(e.getMessage(), e);
        }

        return 0;
    }

    public static long decr(String key, long delta, int initValue) {
        try {
            return memcachedClient.decr(key, delta, initValue);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            logger.error("key : {}, delta : {}, initValue : {}", key, delta, initValue);
            logger.error(e.getMessage(), e);
        }

        return 0;
    }

    public static <T> boolean cas(String key, CASOperation<T> operation) {
        try {
            return memcachedClient.cas(key, operation);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            logger.error("key : {}, CASOperation : {}", key, operation.getClass().getName());
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
