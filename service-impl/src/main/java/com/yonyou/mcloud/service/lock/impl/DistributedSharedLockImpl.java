package com.yonyou.mcloud.service.lock.impl;

import Ice.Current;
import com.yonyou.mcloud.service.lock.LockException;
import com.yonyou.mcloud.service.lock._DistributedSharedLockDisp;
import com.yonyou.mcloud.zookeeper.impl.CuratorZookeeperClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by hubo on 16/1/13
 */
public class DistributedSharedLockImpl extends _DistributedSharedLockDisp {

    private InterProcessLock lock = null;

    private CuratorFramework client;

    private static Logger log = LoggerFactory.getLogger(DistributedSharedLockImpl.class);

    public DistributedSharedLockImpl() {
        client = CuratorZookeeperClientFactory.create();
        client.start();
        lock = new InterProcessMutex(client, "/mcloud/lcok");
    }

    public void destroy() {
        client.close();
    }

    @Override
    public void lock(Current __current) {
        try {
            lock.acquire();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean tryLock(Current __current) {
        try {
            return lock.acquire(-1, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean tryLockInTime(long ms, Current __current) throws LockException {
        try {
            return lock.acquire(ms, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LockException(e);
        }
    }

    @Override
    public void unlock(Current __current) {
        try {
            lock.release();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        DistributedSharedLockImpl l = new DistributedSharedLockImpl();
        TimeUnit.SECONDS.sleep(5);
        l.destroy();
    }
}
