package com.yonyou.mcloud.kafka;

import com.yonyou.mcloud.utils.PropertiesUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duduchao on 16/1/27
 */
public class KafkaProducer<T> {

    private static Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private Producer<String, T> logProducer;

    private final String topic;

    private volatile boolean closed;

    public KafkaProducer(String topic) {
        this.topic = topic;
        logProducer = new Producer<>(new ProducerConfig(PropertiesUtil.createProperties("producer")));
    }

    public void produce(T msg) {
        if(msg == null) {
            throw new IllegalArgumentException("produce msg == null");
        }

        if(log.isDebugEnabled()) {
            log.debug("produce msg[{}] to kafka", msg);
        }

        logProducer.send(new KeyedMessage<String, T>(getTopic(), msg));
    }

    public void produce(List<T> msgs) {
        if(msgs == null) {
            throw new IllegalArgumentException("produce msgs == null");
        }

        if(msgs.isEmpty()) {
            log.warn("produce msg is empty !");
            return;
        }

        if(log.isDebugEnabled()) {
            log.debug("produce msgs[{}] to kafka", msgs);
        }

        List<KeyedMessage<String, T>> messageList = new ArrayList<>();

        for (T msg : msgs) {
            messageList.add(new KeyedMessage<String, T>(getTopic(), msg));
        }

        logProducer.send(messageList);
    }

    public String getTopic() {
        return topic;
    }

    public void close() {
        if (closed) {
            return;
        }

        closed = true;

        try {
            logProducer.close();
        } catch (Throwable t) {
            log.warn(t.getMessage(), t);
        }
    }

}
