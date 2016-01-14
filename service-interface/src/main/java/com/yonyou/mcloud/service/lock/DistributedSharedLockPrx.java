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

public interface DistributedSharedLockPrx extends Ice.ObjectPrx
{
    public void lock();

    public void lock(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_lock();

    public Ice.AsyncResult begin_lock(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_lock(Ice.Callback __cb);

    public Ice.AsyncResult begin_lock(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_lock(Callback_DistributedSharedLock_lock __cb);

    public Ice.AsyncResult begin_lock(java.util.Map<String, String> __ctx, Callback_DistributedSharedLock_lock __cb);

    public Ice.AsyncResult begin_lock(IceInternal.Functional_VoidCallback __responseCb,
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_lock(IceInternal.Functional_VoidCallback __responseCb,
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                      IceInternal.Functional_BoolCallback __sentCb);

    public Ice.AsyncResult begin_lock(java.util.Map<String, String> __ctx,
                                      IceInternal.Functional_VoidCallback __responseCb,
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_lock(java.util.Map<String, String> __ctx,
                                      IceInternal.Functional_VoidCallback __responseCb,
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                      IceInternal.Functional_BoolCallback __sentCb);

    public void end_lock(Ice.AsyncResult __result);

    public boolean tryLock();

    public boolean tryLock(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_tryLock();

    public Ice.AsyncResult begin_tryLock(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_tryLock(Ice.Callback __cb);

    public Ice.AsyncResult begin_tryLock(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_tryLock(Callback_DistributedSharedLock_tryLock __cb);

    public Ice.AsyncResult begin_tryLock(java.util.Map<String, String> __ctx, Callback_DistributedSharedLock_tryLock __cb);

    public Ice.AsyncResult begin_tryLock(IceInternal.Functional_BoolCallback __responseCb,
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_tryLock(IceInternal.Functional_BoolCallback __responseCb,
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                         IceInternal.Functional_BoolCallback __sentCb);

    public Ice.AsyncResult begin_tryLock(java.util.Map<String, String> __ctx,
                                         IceInternal.Functional_BoolCallback __responseCb,
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_tryLock(java.util.Map<String, String> __ctx,
                                         IceInternal.Functional_BoolCallback __responseCb,
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                         IceInternal.Functional_BoolCallback __sentCb);

    public boolean end_tryLock(Ice.AsyncResult __result);

    public boolean tryLockInTime(long ms)
        throws LockException;

    public boolean tryLockInTime(long ms, java.util.Map<String, String> __ctx)
        throws LockException;

    public Ice.AsyncResult begin_tryLockInTime(long ms);

    public Ice.AsyncResult begin_tryLockInTime(long ms, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_tryLockInTime(long ms, Ice.Callback __cb);

    public Ice.AsyncResult begin_tryLockInTime(long ms, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_tryLockInTime(long ms, Callback_DistributedSharedLock_tryLockInTime __cb);

    public Ice.AsyncResult begin_tryLockInTime(long ms, java.util.Map<String, String> __ctx, Callback_DistributedSharedLock_tryLockInTime __cb);

    public Ice.AsyncResult begin_tryLockInTime(long ms,
                                               IceInternal.Functional_BoolCallback __responseCb,
                                               IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb,
                                               IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_tryLockInTime(long ms,
                                               IceInternal.Functional_BoolCallback __responseCb,
                                               IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb,
                                               IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                               IceInternal.Functional_BoolCallback __sentCb);

    public Ice.AsyncResult begin_tryLockInTime(long ms,
                                               java.util.Map<String, String> __ctx,
                                               IceInternal.Functional_BoolCallback __responseCb,
                                               IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb,
                                               IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_tryLockInTime(long ms,
                                               java.util.Map<String, String> __ctx,
                                               IceInternal.Functional_BoolCallback __responseCb,
                                               IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb,
                                               IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                               IceInternal.Functional_BoolCallback __sentCb);

    public boolean end_tryLockInTime(Ice.AsyncResult __result)
        throws LockException;

    public void unlock();

    public void unlock(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_unlock();

    public Ice.AsyncResult begin_unlock(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_unlock(Ice.Callback __cb);

    public Ice.AsyncResult begin_unlock(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_unlock(Callback_DistributedSharedLock_unlock __cb);

    public Ice.AsyncResult begin_unlock(java.util.Map<String, String> __ctx, Callback_DistributedSharedLock_unlock __cb);

    public Ice.AsyncResult begin_unlock(IceInternal.Functional_VoidCallback __responseCb,
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_unlock(IceInternal.Functional_VoidCallback __responseCb,
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                        IceInternal.Functional_BoolCallback __sentCb);

    public Ice.AsyncResult begin_unlock(java.util.Map<String, String> __ctx,
                                        IceInternal.Functional_VoidCallback __responseCb,
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_unlock(java.util.Map<String, String> __ctx,
                                        IceInternal.Functional_VoidCallback __responseCb,
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                        IceInternal.Functional_BoolCallback __sentCb);

    public void end_unlock(Ice.AsyncResult __result);
}
