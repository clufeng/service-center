package com.yonyou.mcloud.utils;

import com.yonyou.mcloud.memcached.MemcachedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具
 * Created by hubo on 2016/3/7
 */
public class ConcurrentUtil {

    private static Logger logger = LoggerFactory.getLogger(ConcurrentUtil.class);

    public <T> T lock(String identify, Callable<T> run) {

        T result = null;

        while (true) {
            if(MemcachedUtils.add(identify, identify, -1)) {
                try {
                    result = run.call();
                    break;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    break;
                } finally {
                    MemcachedUtils.remove(identify);
//                    synchronized ( this ) {
//                        this.notifyAll();
//                    }
                }
            }else {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
//                synchronized (this) {
//                    try {
//                        this.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                }
            }
        }

        return result;
    }

}
