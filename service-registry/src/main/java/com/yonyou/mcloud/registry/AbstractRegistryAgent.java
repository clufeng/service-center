package com.yonyou.mcloud.registry;

import com.yonyou.mcloud.RegistryMeta;
import com.yonyou.mcloud.utils.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hubo on 16/1/26
 */
public abstract class AbstractRegistryAgent implements RegistryAgent {

    private final Logger logger = LoggerFactory.getLogger(AbstractRegistryAgent.class);

    private final Set<RegistryMeta> registryed = new ConcurrentHashSet<>();

    private final Set<RegistryMeta> failedRegistryed = new ConcurrentHashSet<>();
    private final Set<RegistryMeta> failedUnregistryed = new ConcurrentHashSet<>();


    public Set<RegistryMeta> getRegistryed() {
        return registryed;
    }

    @Override
    public void register(RegistryMeta meta) {
        if(meta == null) {
            throw new IllegalArgumentException("registry meta == null");
        }

        failedRegistryed.remove(meta);
        failedUnregistryed.remove(meta);

        if(logger.isInfoEnabled()) {
            logger.info("register : {}", meta);
        }

        registryed.add(meta);

        try {
            doRegister(meta);
        } catch (Exception e) {
            logger.error("failed to register " + meta + " wait retry");
            failedRegistryed.add(meta);
        }

    }

    @Override
    public void unregister(RegistryMeta meta) {
        if(meta == null) {
            throw new IllegalArgumentException("registry meta == null");
        }

        failedRegistryed.remove(meta);
        failedUnregistryed.remove(meta);

        if(logger.isInfoEnabled()) {
            logger.info("unregister : {}", meta);
        }

        registryed.remove(meta);

        try {
            doUnregister(meta);
        }catch (Exception e){
            logger.error("failed to unregister " + meta + " wait retry");
            failedUnregistryed.add(meta);
        }
    }

    @Override
    public boolean isRegistered(RegistryMeta meta) {
        return registryed.contains(meta);
    }

    public void recover() {
        for (RegistryMeta registryMeta : getRegistryed()) {
            failedRegistryed.add(registryMeta);
        }
    }

    public void retry() {
        if(!failedRegistryed.isEmpty()) {
            Set<RegistryMeta> failed = new HashSet<>(failedRegistryed);
            for (RegistryMeta meta : failed) {
                try {
                    doRegister(meta);
                    failedRegistryed.remove(meta);
                } catch (Exception e) {
                    logger.warn("failed to retry register {}, will try again", meta);
                }
            }
        }

        if(!failedUnregistryed.isEmpty()) {
            Set<RegistryMeta> failed = new HashSet<>(failedUnregistryed);
            if(logger.isInfoEnabled()) {
                logger.info("Retry unregister " + failed);
            }
            for (RegistryMeta meta : failed) {
                try {
                    doUnregister(meta);
                    failedUnregistryed.remove(meta);
                }catch (Exception e) {
                    logger.warn("failed to retry unregister {}, will try again", meta);
                }
            }
        }
    }

    @Override
    public void close() {
        if(logger.isInfoEnabled()) {
            logger.info("close registry agent");
        }

        if(!getRegistryed().isEmpty()) {
            for (RegistryMeta meta : getRegistryed()) {
                unregister(meta);
            }
        }

        registryed.clear();
        failedUnregistryed.clear();
        failedRegistryed.clear();
    }

    protected abstract void doUnregister(RegistryMeta meta);

    protected abstract void doRegister(RegistryMeta meta);

}
