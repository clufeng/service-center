package com.yonyou.mcloud.service.common.impl;

import Ice.Current;
import com.yonyou.mcloud.service.common._IdGeneratorDisp;
import com.yonyou.mcloud.service.util.MemcachedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 分布式主键生成器
 * Created by hubo on 16/1/14
 */
public class IdGeneratorImpl extends _IdGeneratorDisp{

    private static final Logger log = LoggerFactory.getLogger(IdGeneratorImpl.class);

    private static final String key_pre = "id_generator_";

    private static final int moniter_sleep_time = 60 * 60; //1个小时

    private String lastTime;

    public IdGeneratorImpl() {
        lastTime = getCurrDateStr();
        Thread monitorThread = new Thread(new MonitorWorker());
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    @Override
    public String nextId(Current __current) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        String curr = formatter.format(new Date());

        String key = key_pre + getCurrDateStr();

        String id = curr + "" + String.format("%06d", MemcachedUtils.incr(key, 1, 0));

        log.debug("id generate : {}", id);

        return id;

    }

    public String getCurrDateStr() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private class MonitorWorker implements Runnable {
        @Override
        public void run() {
            while(!Thread.interrupted()) {

                String currTime = getCurrDateStr();

                if(!currTime.equals(lastTime)) {
                    String oldKey = key_pre + lastTime;
                    MemcachedUtils.remove(oldKey);
                    lastTime = currTime;
                }

                try {
                    TimeUnit.SECONDS.sleep(moniter_sleep_time);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }


}
