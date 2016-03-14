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
 * <p>服务资源定位器</p>
 * <p>由定位器创建服务代理</p>
 *
 * Created by hubo on 16/1/14
 */
public class Locator {

    private static Logger logger = LoggerFactory.getLogger(Locator.class);

    public static final String REGITRY_LOCATOR_KEY = "--Ice.Default.Locator";

    public static final String ICE_CONFIG_KEY = "--Ice.Config";

    public static final String IDLE_TIMEOUT_SECONDS_KEY = "idleTimeOutSeconds";

    private static final String regitry_locator;

    private static final long idleTimeOutSeconds;

    private static final String ice_config;

    static {

        String _regitry_locator;

        long _idleTimeOutSeconds;

        String _ice_config;

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("locator");
            _regitry_locator = REGITRY_LOCATOR_KEY + "=" + bundle.getString(REGITRY_LOCATOR_KEY);
            _idleTimeOutSeconds = Long.valueOf(bundle.getString(IDLE_TIMEOUT_SECONDS_KEY));
            _ice_config = ICE_CONFIG_KEY + "=" + bundle.getString(ICE_CONFIG_KEY);
        }catch (Exception e) {
            logger.warn("找不到默认locator配置文件");
            _regitry_locator = "";
            _idleTimeOutSeconds = 10 * 600;
            _ice_config = "";
        }

        regitry_locator = _regitry_locator;

        idleTimeOutSeconds = _idleTimeOutSeconds;

        ice_config = _ice_config;

    }

    private static Communicator ic;

    private static Map<Class<?>, ObjectPrx> cache = new ConcurrentHashMap<>();

    private static long lastAccessTime;

    private static Thread moniterThread;

    private Locator() {}

    /**
     * 获取通信器
     * @return Communicator
     */
    private static Communicator getCommunicator() {

        if(ic == null) {
            synchronized (Locator.class) {
                if (ic == null) {
                    if (Locator.class.getClassLoader().getResource(ice_config) != null) {
                        ic = Ice.Util.initialize(new String[]{regitry_locator, ice_config});
                    } else {
                        ic = Ice.Util.initialize(new String[]{regitry_locator});
                    }
                    lastAccessTime = System.currentTimeMillis();
                    moniterThread = new Thread(new MoniterThread());
                    moniterThread.setDaemon(true);
                    moniterThread.start();
                }
            }
        }

        return ic;
    }

    /**
     * 关闭通信器
     * @param clearCache 是否清除缓存
     */
    private static void closeCommunicator(boolean clearCache) {
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
            if(clearCache) {
                cache.clear();
            }
        }
    }

    /**
     * 创建代理对象
     *
     * <P>
     *     通过约定来创建代理对象，ice在生成代理对象的时候会加上Prx（Proxy简写）
     *     但是在注册的时候通常是不加Prx。
     * </P>
     *
     * 例如:
     * <p>
     *     IdGeneratorPrx(代理对象) -> IdGenerator（服务注册对象）
     * </p>
     * <p>
     *     CacheServicePrx(代理对象) -> CacheService（服务注册对象）
     * </p>
     *
     * @param ic Communicator
     * @param cls 代理对象类
     * @return 代理对象
     */
    private static ObjectPrx createObjectProxy(Communicator ic, Class<? extends ObjectPrx> cls) {

        String name = cls.getSimpleName();

        //server box 用的idToPrxoy
//        String nameBase = name.substring(0, name.indexOf("Prx"));
//        String idToProxy = nameBase + "Service@" + nameBase + "ServiceAdapter";

        String idToProxy = name.substring(0, name.indexOf("Prx"));

        if(logger.isDebugEnabled()) {
            logger.debug("idToProxy : {}", idToProxy);
        }

        ObjectPrx basePrx = ic.stringToProxy(idToProxy);

        ObjectPrx proxy = null;

        try {
            Object proxyHelper = Class.forName(cls.getName() + "Helper").newInstance();
            Method castMethod = proxyHelper.getClass().getMethod("uncheckedCast", ObjectPrx.class);
            proxy = (ObjectPrx) castMethod.invoke(proxyHelper, basePrx);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return proxy;
    }

    /**
     * 查找代理对象
     * @param cls
     * @param <T>
     * @return
     */
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
                    if(logger.isDebugEnabled()) {
                        logger.debug("close Communicator and clear Cache ...");
                    }
                    closeCommunicator(true);
                }
            }
        }
    }



}
