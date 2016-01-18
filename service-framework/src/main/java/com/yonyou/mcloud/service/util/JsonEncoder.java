package com.yonyou.mcloud.service.util;

import com.alibaba.fastjson.JSON;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * Created by duduchao on 16/1/18
 */
public class JsonEncoder<T> implements Encoder<T> {
    @Override
    public byte[] toBytes(T entry) {

        if(entry == null) {
            throw new NullPointerException("");
        }

        return JSON.toJSONBytes(entry);
    }

    public JsonEncoder(VerifiableProperties props) {

    }
}
