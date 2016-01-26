package com.yonyou.mcloud.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>抽象Zookeeper客户端实现</p>
 *
 * Created by hubo on 16/1/26
 */
public abstract class AbstractZookeeperClient<T> implements ZookeeperClient {

    protected static final Logger log = LoggerFactory.getLogger(AbstractZookeeperClient.class);

    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();

    private final ConcurrentMap<String, ConcurrentMap<ChildListener, T>> childListeners = new ConcurrentHashMap<>();

    private volatile boolean closed = false;

    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    public Set<StateListener> getSessionListeners() {
        return stateListeners;
    }


    @Override
    public void create(String path, boolean ephemeral) {

        if(path == null || path.trim().length() == 0) {
            throw new IllegalArgumentException("zookeeper node == null");
        }

        if(ephemeral) {
            createPersistent(path);
        }else {
            createEphemeral(path);
        }

    }

    public List<String> addChildListener(String path, final ChildListener listener) {
        ConcurrentMap<ChildListener, T> listeners = childListeners.get(path);
        if (listeners == null) {
            childListeners.putIfAbsent(path, new ConcurrentHashMap<ChildListener, T>());
            listeners = childListeners.get(path);
        }
        T targetListener = listeners.get(listener);
        if (targetListener == null) {
            listeners.putIfAbsent(listener, createTargetChildListener(path, listener));
            targetListener = listeners.get(listener);
        }
        return addTargetChildListener(path, targetListener);
    }



    public void removeChildListener(String path, ChildListener listener) {
        ConcurrentMap<ChildListener, T> listeners = childListeners.get(path);
        if (listeners != null) {
            T targetListener = listeners.remove(listener);
            if (targetListener != null) {
                removeTargetChildListener(path, targetListener);
            }
        }
    }

    protected void stateChanged(int state) {
        for (StateListener sessionListener : getSessionListeners()) {
            sessionListener.stateChanged(state);
        }
    }

    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        try {
            doClose();
        } catch (Throwable t) {
            log.warn(t.getMessage(), t);
        }
    }

    protected abstract void doClose();

    protected abstract void createEphemeral(String path);

    protected abstract void createPersistent(String path);

    protected abstract T createTargetChildListener(String path, ChildListener listener);

    protected abstract List<String> addTargetChildListener(String path, T listener);

    protected abstract void removeTargetChildListener(String path, T listener);


}
