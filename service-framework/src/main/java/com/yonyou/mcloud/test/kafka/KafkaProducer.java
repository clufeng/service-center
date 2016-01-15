package com.yonyou.mcloud.test.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by hubo on 16/1/15
 */
public class KafkaProducer {

    private final Producer<String, String> producer;

    public final static String TOPIC = "TEST-TOPIC";

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaProducer() {
        Properties props = new Properties();

        props.put("zk.connect", "192.168.20.17:2181,192.168.20.18:2181,192.168.20.19:2181");
        //此处配置的是kafka的端口
        props.put("metadata.broker.list", "192.168.20.17:9092,192.168.20.18:9092,192.168.20.19:9092");
        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");

        props.put("request.required.acks", "1");

        producer = new Producer<>(new ProducerConfig(props));
    }

    void produce() {

        int messageNo = 1;

        final int COUNT = 10;

        while (messageNo <= COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            producer.send(new KeyedMessage<>(TOPIC, key, data));
            System.out.println(data);
            messageNo++;
        }

    }

    public static void main(String[] args) {
        new KafkaProducer().produce();
    }

}
