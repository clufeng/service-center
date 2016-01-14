package com.yonyou.mcloud.service.common.impl;

import Ice.Current;
import com.yonyou.mcloud.service.common._IdGeneratorDisp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by duduchao on 16/1/14
 */
public class IdGeneratorImpl extends _IdGeneratorDisp{

    @Override
    public String nextId(Current __current) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        String curr = formatter.format(new Date());

        return curr + "" + String.format("%04d", 1);

    }
}
