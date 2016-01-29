package com.yonyou.mcloud.memcached;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 * Created by hubo on 2016/1/4
 */
public class MemcachedUtils {

    private static MemcachedClient memcachedClient;

    /**
     * 不允许创建实例
     */
    private MemcachedUtils() {

    }

    static {
        // 获取memcached服务器列表
        String servers = "192.168.21.2:11211 192.168.21.3:11211";

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
        // 使用二进制协议
        builder.setCommandFactory(new BinaryCommandFactory());
        try {
            memcachedClient = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    public static boolean set(String key, Object value, int expire) {
        try {
            return memcachedClient.set(key, expire, value);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }

    public static boolean remove(String key) {
        try {
            return memcachedClient.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return 0;
    }

    public static long decr(String key, long delta, int initValue) {
        try {
            return memcachedClient.decr(key, delta, initValue);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static <T> boolean cas(String key, CASOperation<T> operation) {
        try {
            return memcachedClient.cas(key, operation);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
