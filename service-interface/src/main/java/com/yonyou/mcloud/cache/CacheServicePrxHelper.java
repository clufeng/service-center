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
// Generated from file `CacheService.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.yonyou.mcloud.cache;

public final class CacheServicePrxHelper extends Ice.ObjectPrxHelperBase implements CacheServicePrx
{
    private static final String __add_name = "add";

    public boolean add(String key, java.io.Serializable value, int expire)
        throws CacheException
    {
        return add(key, value, expire, null, false);
    }

    public boolean add(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx)
        throws CacheException
    {
        return add(key, value, expire, __ctx, true);
    }

    private boolean add(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx, boolean __explicitCtx)
        throws CacheException
    {
        __checkTwowayOnly(__add_name);
        return end_add(begin_add(key, value, expire, __ctx, __explicitCtx, true, null));
    }

    public Ice.AsyncResult begin_add(String key, java.io.Serializable value, int expire)
    {
        return begin_add(key, value, expire, null, false, false, null);
    }

    public Ice.AsyncResult begin_add(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx)
    {
        return begin_add(key, value, expire, __ctx, true, false, null);
    }

    public Ice.AsyncResult begin_add(String key, java.io.Serializable value, int expire, Ice.Callback __cb)
    {
        return begin_add(key, value, expire, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_add(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_add(key, value, expire, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_add(String key, java.io.Serializable value, int expire, Callback_CacheService_add __cb)
    {
        return begin_add(key, value, expire, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_add(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx, Callback_CacheService_add __cb)
    {
        return begin_add(key, value, expire, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_add(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_add(key, value, expire, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_add(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                     IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_add(key, value, expire, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    public Ice.AsyncResult begin_add(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     java.util.Map<String, String> __ctx, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_add(key, value, expire, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_add(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     java.util.Map<String, String> __ctx, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                     IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_add(key, value, expire, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    private Ice.AsyncResult begin_add(String key, 
                                      java.io.Serializable value, 
                                      int expire, 
                                      java.util.Map<String, String> __ctx, 
                                      boolean __explicitCtx, 
                                      boolean __synchronous, 
                                      IceInternal.Functional_BoolCallback __responseCb, 
                                      IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                      IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_add(key, value, expire, __ctx, __explicitCtx, __synchronous, 
                         new IceInternal.Functional_TwowayCallbackBoolUE(__responseCb, __userExceptionCb, __exceptionCb, __sentCb)
                             {
                                 public final void __completed(Ice.AsyncResult __result)
                                 {
                                     CacheServicePrxHelper.__add_completed(this, __result);
                                 }
                             });
    }

    private Ice.AsyncResult begin_add(String key, 
                                      java.io.Serializable value, 
                                      int expire, 
                                      java.util.Map<String, String> __ctx, 
                                      boolean __explicitCtx, 
                                      boolean __synchronous, 
                                      IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__add_name);
        IceInternal.OutgoingAsync __result = getOutgoingAsync(__add_name, __cb);
        try
        {
            __result.prepare(__add_name, Ice.OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            IceInternal.BasicStream __os = __result.startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(key);
            __os.writeSerializable(value);
            __os.writeInt(expire);
            __result.endWriteParams();
            __result.invoke();
        }
        catch(Ice.Exception __ex)
        {
            __result.abort(__ex);
        }
        return __result;
    }

    public boolean end_add(Ice.AsyncResult __iresult)
        throws CacheException
    {
        IceInternal.OutgoingAsync __result = IceInternal.OutgoingAsync.check(__iresult, this, __add_name);
        try
        {
            if(!__result.__wait())
            {
                try
                {
                    __result.throwUserException();
                }
                catch(CacheException __ex)
                {
                    throw __ex;
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.startReadParams();
            boolean __ret;
            __ret = __is.readBool();
            __result.endReadParams();
            return __ret;
        }
        finally
        {
            if(__result != null)
            {
                __result.cacheMessageBuffers();
            }
        }
    }

    static public void __add_completed(Ice.TwowayCallbackBoolUE __cb, Ice.AsyncResult __result)
    {
        CacheServicePrx __proxy = (CacheServicePrx)__result.getProxy();
        boolean __ret = false;
        try
        {
            __ret = __proxy.end_add(__result);
        }
        catch(Ice.UserException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.LocalException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.SystemException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        __cb.response(__ret);
    }

    private static final String __evict_name = "evict";

    public boolean evict(String key)
        throws CacheException
    {
        return evict(key, null, false);
    }

    public boolean evict(String key, java.util.Map<String, String> __ctx)
        throws CacheException
    {
        return evict(key, __ctx, true);
    }

    private boolean evict(String key, java.util.Map<String, String> __ctx, boolean __explicitCtx)
        throws CacheException
    {
        __checkTwowayOnly(__evict_name);
        return end_evict(begin_evict(key, __ctx, __explicitCtx, true, null));
    }

    public Ice.AsyncResult begin_evict(String key)
    {
        return begin_evict(key, null, false, false, null);
    }

    public Ice.AsyncResult begin_evict(String key, java.util.Map<String, String> __ctx)
    {
        return begin_evict(key, __ctx, true, false, null);
    }

    public Ice.AsyncResult begin_evict(String key, Ice.Callback __cb)
    {
        return begin_evict(key, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_evict(String key, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_evict(key, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_evict(String key, Callback_CacheService_evict __cb)
    {
        return begin_evict(key, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_evict(String key, java.util.Map<String, String> __ctx, Callback_CacheService_evict __cb)
    {
        return begin_evict(key, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_evict(String key, 
                                       IceInternal.Functional_BoolCallback __responseCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_evict(key, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_evict(String key, 
                                       IceInternal.Functional_BoolCallback __responseCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                       IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_evict(key, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    public Ice.AsyncResult begin_evict(String key, 
                                       java.util.Map<String, String> __ctx, 
                                       IceInternal.Functional_BoolCallback __responseCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_evict(key, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_evict(String key, 
                                       java.util.Map<String, String> __ctx, 
                                       IceInternal.Functional_BoolCallback __responseCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                       IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                       IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_evict(key, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    private Ice.AsyncResult begin_evict(String key, 
                                        java.util.Map<String, String> __ctx, 
                                        boolean __explicitCtx, 
                                        boolean __synchronous, 
                                        IceInternal.Functional_BoolCallback __responseCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                        IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                        IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_evict(key, __ctx, __explicitCtx, __synchronous, 
                           new IceInternal.Functional_TwowayCallbackBoolUE(__responseCb, __userExceptionCb, __exceptionCb, __sentCb)
                               {
                                   public final void __completed(Ice.AsyncResult __result)
                                   {
                                       CacheServicePrxHelper.__evict_completed(this, __result);
                                   }
                               });
    }

    private Ice.AsyncResult begin_evict(String key, 
                                        java.util.Map<String, String> __ctx, 
                                        boolean __explicitCtx, 
                                        boolean __synchronous, 
                                        IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__evict_name);
        IceInternal.OutgoingAsync __result = getOutgoingAsync(__evict_name, __cb);
        try
        {
            __result.prepare(__evict_name, Ice.OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            IceInternal.BasicStream __os = __result.startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(key);
            __result.endWriteParams();
            __result.invoke();
        }
        catch(Ice.Exception __ex)
        {
            __result.abort(__ex);
        }
        return __result;
    }

    public boolean end_evict(Ice.AsyncResult __iresult)
        throws CacheException
    {
        IceInternal.OutgoingAsync __result = IceInternal.OutgoingAsync.check(__iresult, this, __evict_name);
        try
        {
            if(!__result.__wait())
            {
                try
                {
                    __result.throwUserException();
                }
                catch(CacheException __ex)
                {
                    throw __ex;
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.startReadParams();
            boolean __ret;
            __ret = __is.readBool();
            __result.endReadParams();
            return __ret;
        }
        finally
        {
            if(__result != null)
            {
                __result.cacheMessageBuffers();
            }
        }
    }

    static public void __evict_completed(Ice.TwowayCallbackBoolUE __cb, Ice.AsyncResult __result)
    {
        CacheServicePrx __proxy = (CacheServicePrx)__result.getProxy();
        boolean __ret = false;
        try
        {
            __ret = __proxy.end_evict(__result);
        }
        catch(Ice.UserException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.LocalException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.SystemException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        __cb.response(__ret);
    }

    private static final String __get_name = "get";

    public java.io.Serializable get(String key)
        throws CacheException
    {
        return get(key, null, false);
    }

    public java.io.Serializable get(String key, java.util.Map<String, String> __ctx)
        throws CacheException
    {
        return get(key, __ctx, true);
    }

    private java.io.Serializable get(String key, java.util.Map<String, String> __ctx, boolean __explicitCtx)
        throws CacheException
    {
        __checkTwowayOnly(__get_name);
        return end_get(begin_get(key, __ctx, __explicitCtx, true, null));
    }

    public Ice.AsyncResult begin_get(String key)
    {
        return begin_get(key, null, false, false, null);
    }

    public Ice.AsyncResult begin_get(String key, java.util.Map<String, String> __ctx)
    {
        return begin_get(key, __ctx, true, false, null);
    }

    public Ice.AsyncResult begin_get(String key, Ice.Callback __cb)
    {
        return begin_get(key, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_get(String key, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_get(key, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_get(String key, Callback_CacheService_get __cb)
    {
        return begin_get(key, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_get(String key, java.util.Map<String, String> __ctx, Callback_CacheService_get __cb)
    {
        return begin_get(key, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_get(String key, 
                                     IceInternal.Functional_GenericCallback1<java.io.Serializable> __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_get(key, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_get(String key, 
                                     IceInternal.Functional_GenericCallback1<java.io.Serializable> __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                     IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_get(key, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    public Ice.AsyncResult begin_get(String key, 
                                     java.util.Map<String, String> __ctx, 
                                     IceInternal.Functional_GenericCallback1<java.io.Serializable> __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_get(key, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_get(String key, 
                                     java.util.Map<String, String> __ctx, 
                                     IceInternal.Functional_GenericCallback1<java.io.Serializable> __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                     IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_get(key, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    private Ice.AsyncResult begin_get(String key, 
                                      java.util.Map<String, String> __ctx, 
                                      boolean __explicitCtx, 
                                      boolean __synchronous, 
                                      IceInternal.Functional_GenericCallback1<java.io.Serializable> __responseCb, 
                                      IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                      IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_get(key, __ctx, __explicitCtx, __synchronous, 
                         new IceInternal.Functional_TwowayCallbackArg1UE<java.io.Serializable>(__responseCb, __userExceptionCb, __exceptionCb, __sentCb)
                             {
                                 public final void __completed(Ice.AsyncResult __result)
                                 {
                                     CacheServicePrxHelper.__get_completed(this, __result);
                                 }
                             });
    }

    private Ice.AsyncResult begin_get(String key, 
                                      java.util.Map<String, String> __ctx, 
                                      boolean __explicitCtx, 
                                      boolean __synchronous, 
                                      IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__get_name);
        IceInternal.OutgoingAsync __result = getOutgoingAsync(__get_name, __cb);
        try
        {
            __result.prepare(__get_name, Ice.OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            IceInternal.BasicStream __os = __result.startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(key);
            __result.endWriteParams();
            __result.invoke();
        }
        catch(Ice.Exception __ex)
        {
            __result.abort(__ex);
        }
        return __result;
    }

    public java.io.Serializable end_get(Ice.AsyncResult __iresult)
        throws CacheException
    {
        IceInternal.OutgoingAsync __result = IceInternal.OutgoingAsync.check(__iresult, this, __get_name);
        try
        {
            if(!__result.__wait())
            {
                try
                {
                    __result.throwUserException();
                }
                catch(CacheException __ex)
                {
                    throw __ex;
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.startReadParams();
            java.io.Serializable __ret;
            __ret = (java.io.Serializable)__is.readSerializable();
            __result.endReadParams();
            return __ret;
        }
        finally
        {
            if(__result != null)
            {
                __result.cacheMessageBuffers();
            }
        }
    }

    static public void __get_completed(Ice.TwowayCallbackArg1UE<java.io.Serializable> __cb, Ice.AsyncResult __result)
    {
        CacheServicePrx __proxy = (CacheServicePrx)__result.getProxy();
        java.io.Serializable __ret = null;
        try
        {
            __ret = __proxy.end_get(__result);
        }
        catch(Ice.UserException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.LocalException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.SystemException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        __cb.response(__ret);
    }

    private static final String __set_name = "set";

    public boolean set(String key, java.io.Serializable value, int expire)
        throws CacheException
    {
        return set(key, value, expire, null, false);
    }

    public boolean set(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx)
        throws CacheException
    {
        return set(key, value, expire, __ctx, true);
    }

    private boolean set(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx, boolean __explicitCtx)
        throws CacheException
    {
        __checkTwowayOnly(__set_name);
        return end_set(begin_set(key, value, expire, __ctx, __explicitCtx, true, null));
    }

    public Ice.AsyncResult begin_set(String key, java.io.Serializable value, int expire)
    {
        return begin_set(key, value, expire, null, false, false, null);
    }

    public Ice.AsyncResult begin_set(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx)
    {
        return begin_set(key, value, expire, __ctx, true, false, null);
    }

    public Ice.AsyncResult begin_set(String key, java.io.Serializable value, int expire, Ice.Callback __cb)
    {
        return begin_set(key, value, expire, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_set(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_set(key, value, expire, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_set(String key, java.io.Serializable value, int expire, Callback_CacheService_set __cb)
    {
        return begin_set(key, value, expire, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_set(String key, java.io.Serializable value, int expire, java.util.Map<String, String> __ctx, Callback_CacheService_set __cb)
    {
        return begin_set(key, value, expire, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_set(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_set(key, value, expire, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_set(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                     IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_set(key, value, expire, null, false, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    public Ice.AsyncResult begin_set(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     java.util.Map<String, String> __ctx, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_set(key, value, expire, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_set(String key, 
                                     java.io.Serializable value, 
                                     int expire, 
                                     java.util.Map<String, String> __ctx, 
                                     IceInternal.Functional_BoolCallback __responseCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                     IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                     IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_set(key, value, expire, __ctx, true, false, __responseCb, __userExceptionCb, __exceptionCb, __sentCb);
    }

    private Ice.AsyncResult begin_set(String key, 
                                      java.io.Serializable value, 
                                      int expire, 
                                      java.util.Map<String, String> __ctx, 
                                      boolean __explicitCtx, 
                                      boolean __synchronous, 
                                      IceInternal.Functional_BoolCallback __responseCb, 
                                      IceInternal.Functional_GenericCallback1<Ice.UserException> __userExceptionCb, 
                                      IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                      IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_set(key, value, expire, __ctx, __explicitCtx, __synchronous, 
                         new IceInternal.Functional_TwowayCallbackBoolUE(__responseCb, __userExceptionCb, __exceptionCb, __sentCb)
                             {
                                 public final void __completed(Ice.AsyncResult __result)
                                 {
                                     CacheServicePrxHelper.__set_completed(this, __result);
                                 }
                             });
    }

    private Ice.AsyncResult begin_set(String key, 
                                      java.io.Serializable value, 
                                      int expire, 
                                      java.util.Map<String, String> __ctx, 
                                      boolean __explicitCtx, 
                                      boolean __synchronous, 
                                      IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__set_name);
        IceInternal.OutgoingAsync __result = getOutgoingAsync(__set_name, __cb);
        try
        {
            __result.prepare(__set_name, Ice.OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            IceInternal.BasicStream __os = __result.startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(key);
            __os.writeSerializable(value);
            __os.writeInt(expire);
            __result.endWriteParams();
            __result.invoke();
        }
        catch(Ice.Exception __ex)
        {
            __result.abort(__ex);
        }
        return __result;
    }

    public boolean end_set(Ice.AsyncResult __iresult)
        throws CacheException
    {
        IceInternal.OutgoingAsync __result = IceInternal.OutgoingAsync.check(__iresult, this, __set_name);
        try
        {
            if(!__result.__wait())
            {
                try
                {
                    __result.throwUserException();
                }
                catch(CacheException __ex)
                {
                    throw __ex;
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.startReadParams();
            boolean __ret;
            __ret = __is.readBool();
            __result.endReadParams();
            return __ret;
        }
        finally
        {
            if(__result != null)
            {
                __result.cacheMessageBuffers();
            }
        }
    }

    static public void __set_completed(Ice.TwowayCallbackBoolUE __cb, Ice.AsyncResult __result)
    {
        CacheServicePrx __proxy = (CacheServicePrx)__result.getProxy();
        boolean __ret = false;
        try
        {
            __ret = __proxy.end_set(__result);
        }
        catch(Ice.UserException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.LocalException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        catch(Ice.SystemException __ex)
        {
            __cb.exception(__ex);
            return;
        }
        __cb.response(__ret);
    }

    public static CacheServicePrx checkedCast(Ice.ObjectPrx __obj)
    {
        return checkedCastImpl(__obj, ice_staticId(), CacheServicePrx.class, CacheServicePrxHelper.class);
    }

    public static CacheServicePrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        return checkedCastImpl(__obj, __ctx, ice_staticId(), CacheServicePrx.class, CacheServicePrxHelper.class);
    }

    public static CacheServicePrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        return checkedCastImpl(__obj, __facet, ice_staticId(), CacheServicePrx.class, CacheServicePrxHelper.class);
    }

    public static CacheServicePrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        return checkedCastImpl(__obj, __facet, __ctx, ice_staticId(), CacheServicePrx.class, CacheServicePrxHelper.class);
    }

    public static CacheServicePrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        return uncheckedCastImpl(__obj, CacheServicePrx.class, CacheServicePrxHelper.class);
    }

    public static CacheServicePrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        return uncheckedCastImpl(__obj, __facet, CacheServicePrx.class, CacheServicePrxHelper.class);
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::cache::CacheService"
    };

    public static String ice_staticId()
    {
        return __ids[1];
    }

    public static void __write(IceInternal.BasicStream __os, CacheServicePrx v)
    {
        __os.writeProxy(v);
    }

    public static CacheServicePrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            CacheServicePrxHelper result = new CacheServicePrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}