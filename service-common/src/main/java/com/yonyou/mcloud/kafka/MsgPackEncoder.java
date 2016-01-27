package com.yonyou.mcloud.kafka;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by hubo on 16/1/20
 */
public class MsgPackEncoder<T> implements Encoder<T> {

    private static Logger logger = LoggerFactory.getLogger(MsgPackEncoder.class);

    private VerifiableProperties props;

    public MsgPackEncoder(VerifiableProperties props) {
        this.props = props;
    }

    @Override
    public byte[] toBytes(T t) {

        if(t == null) {
            throw new NullPointerException();
        }

        if(logger.isDebugEnabled()) {
            logger.debug("msgpack object to byte : {}", t);
        }

        try {
            return new MessagePack().write(t);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public VerifiableProperties getProps() {
        return props;
    }
}
