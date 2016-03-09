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

public interface _CacheServiceOperations
{
    java.io.Serializable get(String key, Ice.Current __current)
        throws CacheException;

    boolean add(String key, java.io.Serializable value, int expire, Ice.Current __current)
        throws CacheException;

    boolean set(String key, java.io.Serializable value, int expire, Ice.Current __current)
        throws CacheException;

    boolean evict(String key, Ice.Current __current)
        throws CacheException;
}
