package com.yonyou.mcloud.service;

import Ice.Communicator;
import Ice.ObjectPrx;
import com.yonyou.mcloud.service.common.IdGeneratorPrx;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by hubo on 16/1/14
 */
public class Locator {

    public static final String REGITRY_LOCATOR_KEY = "--Ice.Default.Locator";

    public static final String IDLE_TIMEOUT_SECONDS_KEY = "idleTimeOutSeconds";

    private static final String regitry_locator;

    private static final long idleTimeOutSeconds;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("locator");

        regitry_locator = REGITRY_LOCATOR_KEY + "=" + bundle.getString(REGITRY_LOCATOR_KEY);

        idleTimeOutSeconds = Long.valueOf(bundle.getString(IDLE_TIMEOUT_SECONDS_KEY));
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
//                    moniterThread.start();
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

        Ice.ObjectPrx basePrx = ic.stringToProxy(idToProxy);

        ObjectPrx proxy = null;

        try {
            Object proxyHelper = Class.forName(cls.getName() + "Helper").newInstance();
            Method castMethod = proxyHelper.getClass().getMethod("uncheckedCast", Ice.ObjectPrx.class);
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


    public static void main(String[] args) {

        IdGeneratorPrx idGeneratorPrx = Locator.lookup(IdGeneratorPrx.class);

        for (int i = 0; i < 1; i++) {
            String id = idGeneratorPrx.nextId();
            System.out.println("id is :" + id);
        }

        closeCommunicator(true);

    }
}
