package com.yonyou.mcloud.zookeeper;

/**
 * Created by duduchao on 16/1/26
 */
public interface StateListener {

    int DISCONNECTED = 0;

    int CONNECTED = 1;

    int RECONNECTED = 2;

    void stateChanged(int state);

}
