package com.yonyou.mcloud;

import Ice.Communicator;
import Ice.ObjectPrx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by hubo on 16/1/14
 */
public class Locator {

    private static Logger logger = LoggerFactory.getLogger(Locator.class);

    public static final String REGITRY_LOCATOR_KEY = "--Ice.Default.Locator";

    public static final String IDLE_TIMEOUT_SECONDS_KEY = "idleTimeOutSeconds";

    private static final String regitry_locator;

    private static final long idleTimeOutSeconds;

    static {

        String _regitry_locator;

        long _idleTimeOutSeconds;

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("locator");
            _regitry_locator = REGITRY_LOCATOR_KEY + "=" + bundle.getString(REGITRY_LOCATOR_KEY);
            _idleTimeOutSeconds = Long.valueOf(bundle.getString(IDLE_TIMEOUT_SECONDS_KEY));
        }catch (Exception e) {
            logger.warn("找不到默认locator配置文件");
            _regitry_locator = "";
            _idleTimeOutSeconds = 10 * 600;
        }

        regitry_locator = _regitry_locator;

        idleTimeOutSeconds = _idleTimeOutSeconds;
    }

    private static Communicator ic;

    private static Map<Class<?>, ObjectPrx> cache = new ConcurrentHashMap<>();

    private static long lastAccessTime;

    private static Thread moniterThread;

    private Locator() {}

    private static Communicator getCommunicator() {

        if(ic == null) {
            synchronized (Locator.class) {
                if (ic == null) {
                    ic = Ice.Util.initialize(new String[]{regitry_locator});
                    lastAccessTime = System.currentTimeMillis();
                    moniterThread = new Thread(new MoniterThread());
                    moniterThread.setDaemon(true);
                    moniterThread.start();
                }
            }
        }

        return ic;
    }

    private static void closeCommunicator(boolean removeCache) {
        synchronized (Locator.class) {
            if(ic != null){
                try{
                    ic.shutdown();
                }finally {
                    ic.destroy();
                    ic = null;
                }
            }
            if(moniterThread != null) {
                moniterThread.interrupt();
            }
            if(removeCache) {
                cache.clear();
            }
        }
    }

    private static ObjectPrx createObjectProxy(Communicator ic, Class<? extends ObjectPrx> cls) {

        String name = cls.getSimpleName();

        String nameBase = name.substring(0, name.indexOf("Prx"));

        String idToProxy = nameBase + "Service@" + nameBase + "ServiceAdapter";

        ObjectPrx basePrx = ic.stringToProxy(idToProxy);

        ObjectPrx proxy = null;

        try {
            Object proxyHelper = Class.forName(cls.getName() + "Helper").newInstance();
            Method castMethod = proxyHelper.getClass().getMethod("uncheckedCast", ObjectPrx.class);
            proxy = (ObjectPrx) castMethod.invoke(proxyHelper, basePrx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return proxy;
    }

    @SuppressWarnings("unchecked")
    public static <T extends ObjectPrx> T lookup(Class<T> cls) {

        if(cache.containsKey(cls)) {
            lastAccessTime = System.currentTimeMillis();
            return (T) cache.get(cls);
        }

        ObjectPrx proxy = createObjectProxy(getCommunicator(), cls);

        cache.put(cls, proxy);

        lastAccessTime = System.currentTimeMillis();

        return (T) proxy;
    }


    private static class MoniterThread implements Runnable {
        @Override
        public void run() {
            while(!Thread.interrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if(lastAccessTime + idleTimeOutSeconds * 1000L < System.currentTimeMillis()) {
                    closeCommunicator(true);
                }
            }
        }
    }



}
