// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

package com.yonyou.mcloud.monitor.observer;

import IceGrid.AdapterDynamicInfo;
import IceGrid.NodeDynamicInfo;
import IceGrid.ServerDynamicInfo;
import IceGrid._NodeObserverDisp;

public class NodeObserverI extends _NodeObserverDisp {


    @Override
    public void nodeInit(final NodeDynamicInfo[] nodes, Ice.Current current) {

    }

    @Override
    public void nodeUp(final NodeDynamicInfo nodeInfo, Ice.Current current) {

    }

    @Override
    public void nodeDown(final String nodeName, Ice.Current current) {

    }

    @Override
    public void updateServer(final String node, final ServerDynamicInfo updatedInfo, Ice.Current current) {

    }

    @Override
    public void updateAdapter(final String node, final AdapterDynamicInfo updatedInfo, Ice.Current current) {

    }

}
