// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

package com.yonyou.mcloud.monitor.observer;

import IceGrid.AdapterInfo;
import IceGrid._AdapterObserverDisp;

public class AdapterObserverI extends _AdapterObserverDisp {

    @Override
    public synchronized void adapterInit(final AdapterInfo[] adapters, Ice.Current current) {
        for (AdapterInfo adapter : adapters) {
            System.out.println(adapter.id + ":" + adapter.replicaGroupId);
            System.out.println(adapter.proxy);
        }
    }

    @Override
    public void adapterAdded(final AdapterInfo info, Ice.Current current) {
        System.out.println("add adapter : " + info.id);
    }

    @Override
    public void adapterUpdated(final AdapterInfo info, Ice.Current current) {

    }

    @Override
    public void adapterRemoved(final String id, Ice.Current current) {

    }

}
