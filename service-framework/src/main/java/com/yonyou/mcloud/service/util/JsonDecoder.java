package com.yonyou.mcloud.service.util;

import com.alibaba.fastjson.JSON;
import kafka.serializer.Decoder;

/**
 * <p></p>
 * Created by hubo on 16/1/18
 */
public class JsonDecoder<T> implements Decoder<T> {

    private final Class<T> cls;

    public JsonDecoder(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T fromBytes(byte[] bytes) {
        return JSON.parseObject(bytes, cls);
    }

}
