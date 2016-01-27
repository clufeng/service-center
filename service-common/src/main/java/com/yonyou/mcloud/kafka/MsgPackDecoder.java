package com.yonyou.mcloud.kafka;

import kafka.serializer.Decoder;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by hubo on 16/1/20
 */
public class MsgPackDecoder<T> implements Decoder<T> {

    private static Logger logger = LoggerFactory.getLogger(MsgPackDecoder.class);

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

        if(logger.isDebugEnabled()) {
            logger.debug("msgpack byte to object : {}", t);
        }

        return t;
    }
}
