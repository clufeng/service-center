package com.yonyou.mcloud.service.util;

import kafka.serializer.Decoder;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * Created by duduchao on 16/1/20
 */
public class MsgPackDecoder<T> implements Decoder<T> {

    private Class<T> cls;

    public MsgPackDecoder(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T fromBytes(byte[] bytes) {

        if(bytes == null) {
            throw new NullPointerException();
        }

        T t = null;

        MessagePack mp = new MessagePack();

        try {
            t = mp.read(bytes, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }
}
