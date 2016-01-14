// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.1
//
// <auto-generated>
//
// Generated from file `DistributedSharedLock.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.yonyou.mcloud.service.lock;

public interface _DistributedSharedLockOperations
{
    void lock(Ice.Current __current);

    boolean tryLock(Ice.Current __current);

    boolean tryLockInTime(long ms, Ice.Current __current)
        throws LockException;

    void unlock(Ice.Current __current);
}
