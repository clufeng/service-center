package com.yonyou.mcloud.utils;

import com.yonyou.mcloud.memcached.MemcachedUtils;
import net.rubyeye.xmemcached.CASOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * memcached 实现分布式主键生成器
 * Created by hubo on 16/1/29
 */
public class MemcacheIdGenerator implements DistributedIdGenerator {

    public String defaultSysModuleCode;

    private static final Logger log = LoggerFactory.getLogger(MemcacheIdGenerator.class);

    private String keyPre = "id_generator_";

    private int moniterSleepTime; //1个小时

    private static String modules_key = "MODULEKEYS";

    private Set<String> moduleCache = new CopyOnWriteArraySet<>();

    private String lastTime;

    static {
        Set<String> moduleSet = MemcachedUtils.get(modules_key);
        if(moduleSet == null) {
            moduleSet = new HashSet<>();
            MemcachedUtils.add(modules_key, moduleSet, 0);
        }
    }

    public MemcacheIdGenerator() {
        lastTime = getCurrDateStr();
        Properties config = PropertiesUtil.createProperties("idgenerator");

        modules_key = (String) config.get("moduleKey");
        defaultSysModuleCode = (String) config.get("defaultSysModuleCode");

        boolean clearTheardEnable = true;

        if(config.get("clearTheardEnable") != null) {
            clearTheardEnable = Boolean.valueOf(config.get("clearTheardEnable").toString());
        }

        if(clearTheardEnable) {
            if(config.get("moniterSleepTime") != null) {
                moniterSleepTime =  Integer.valueOf(config.get("moniterSleepTime").toString());
            }
            Thread monitorThread = new Thread(new MonitorWorker());
            monitorThread.setDaemon(true);
            monitorThread.start();
        }

        updateModuleCache();
    }

    private void updateModuleCache() {
        Set<String> moduleSet = MemcachedUtils.get(modules_key);
        if(moduleSet != null) {
            moduleCache.addAll(moduleSet);
        }
    }

    @Override
    public String nextId(final String module) {

        if(!moduleCache.contains(module)) {

            updateModuleCache();

            if(!moduleCache.contains(module)) {
                MemcachedUtils.cas(modules_key, new CASOperation<Set<String>>() {
                    @Override
                    public int getMaxTries() {
                        return 1;
                    }

                    @Override
                    public Set<String> getNewValue(long currentCAS, Set<String> currentValue) {
                        if(currentValue == null) {
                            currentValue = new HashSet<>();
                        }
                        currentValue.add(module);
                        return currentValue;
                    }
                });
                updateModuleCache();
            }

        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        String curr = formatter.format(new Date());

        String key = keyPre + getCurrDateStr() + "_" + module;

        //为了效率考虑,每天同类数据只能生产1百万条主键ID,应该可以满足95%以上的场景,所以不用加锁
        String id = module + curr  + String.format("%06d", MemcachedUtils.incr(key, 1, 1));

        log.debug("id generate : {}", id);

        return id;

    }

    @Override
    public String nextId() {
        return nextId(defaultSysModuleCode);
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

                    Set<String> moduleSet = MemcachedUtils.get(modules_key);
                    //删除各个模块的计数器
                    for (String module : moduleSet) {
                        String oldKey = keyPre + lastTime + "_" + module;
                        MemcachedUtils.remove(oldKey);
                    }

                    lastTime = currTime;
                }

                try {
                    TimeUnit.SECONDS.sleep(moniterSleepTime);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
