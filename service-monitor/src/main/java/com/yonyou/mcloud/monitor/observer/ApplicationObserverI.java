// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

package com.yonyou.mcloud.monitor.observer;

import IceGrid.ApplicationInfo;
import IceGrid.ApplicationUpdateInfo;
import IceGrid._ApplicationObserverDisp;

public class ApplicationObserverI extends _ApplicationObserverDisp {

    @Override
    public synchronized void applicationInit(int serial, java.util.List<ApplicationInfo> applications,
                                             Ice.Current current) {

    }

    @Override
    public void applicationAdded(final int serial, final ApplicationInfo info, Ice.Current current) {

    }

    @Override
    public void applicationRemoved(final int serial, final String name, final Ice.Current current) {

    }

    @Override
    public void applicationUpdated(final int serial, final ApplicationUpdateInfo info, Ice.Current current) {

    }

}
