package com.yonyou.mcloud.service.util;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * Created by duduchao on 16/1/20
 */
public class MsgPackEncoder<T> implements Encoder<T> {

    public MsgPackEncoder(VerifiableProperties props) {

    }

    @Override
    public byte[] toBytes(T t) {

        if(t == null) {
            throw new NullPointerException();
        }

        try {
            return new MessagePack().write(t);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
