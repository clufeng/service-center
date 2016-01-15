package com.yonyou.mcloud.service.util;

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

    public static void main(String[] args) throws InterruptedException, MemcachedException, TimeoutException, IOException {
//        MemcachedUtils.add("9999", Arrays.asList("111","222"), 0);
//            int threadCount = 1;
//            CountDownLatch l = new CountDownLatch(threadCount);
//            ExecutorService exec = Executors.newFixedThreadPool(threadCount);
//            long t1 = System.currentTimeMillis();
//            for (int i = 0; i < threadCount; i++) {
//                exec.execute(() -> {
//                    try {
//                        List<String> value = MemcachedUtils2.get("99999");
//                        System.out.println(value);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    l.countDown();
//                });
//            }
//            l.await();
//            long t2 = System.currentTimeMillis();
//            System.out.println(t2-t1);
//            MemcachedUtils2.remove("99999");
//            List<String> value = MemcachedUtils2.get("99999");
//            System.out.println(MemcachedUtils.remove("id_generator"));
//            MemcachedUtils.destory();
//            exec.shutdown();
    }

}
