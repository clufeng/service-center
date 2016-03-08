// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

package com.yonyou.mcloud.monitor.observer;

import IceGrid.ObjectInfo;
import IceGrid._ObjectObserverDisp;

public class ObjectObserverI extends _ObjectObserverDisp {

    @Override
    public synchronized void objectInit(final ObjectInfo[] objects, Ice.Current current) {
        for (ObjectInfo object : objects) {
            System.out.println("object : " + object.type);
            System.out.println(object.proxy);
        }
    }

    @Override
    public void objectAdded(final ObjectInfo info, Ice.Current current) {
    }

    @Override
    public void objectUpdated(final ObjectInfo info, Ice.Current current) {
    }

    @Override
    public void objectRemoved(final Ice.Identity id, Ice.Current current) {
    }

}
