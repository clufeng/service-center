package com.yonyou.mcloud.zookeeper;

import java.util.List;

/**
 * Created by hubo on 16/1/26
 */
public interface ChildListener {
    void childChanged(String path, List<String> children);
}
