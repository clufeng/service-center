package com.yonyou.mcloud.utils;

import com.yonyou.mcloud.zookeeper.impl.CuratorZookeeperClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁组件
 * Created by hubo on 16/1/29
 */
public class DistributedLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    private InterProcessLock lock = null;

    private static CuratorFramework client;

    private final String lockPath;

    static {
        client = CuratorZookeeperClientFactory.create();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                client.close();
            }
        }));
    }

    public DistributedLock(String lockPath) {
        this.lockPath = lockPath;
        lock = new InterProcessMutex(client, lockPath);
    }

    @Override
    public void lock() {
        try {
            lock.acquire();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean acquire(long time, TimeUnit unit) throws Exception{
        return lock.acquire(time, unit);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public boolean tryLock() {

        try {
            return tryLock(-1, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //igone
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            return lock.acquire(time, unit);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //igone
        }
        return false;
    }

    @Override
    public void unlock() {
        try {
            lock.release();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //igone
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("不支持此操作");
    }

    public String getLockPath() {
        return lockPath;
    }

}
