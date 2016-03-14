package com.yonyou.mcloud.kafka;

import com.yonyou.mcloud.ServiceExecLog;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Kafka 消费者用于测试
 * Created by hubo on 16/1/15
 */
public class KafkaConsumer {

    private final ConsumerConnector consumer;

    private KafkaConsumer() {
        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", "mq1:2181,mq2:2181,mq3:2181");
        //group 代表一个消费组
        props.put("group.id", "gourp-1");
        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        //序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ConsumerConfig config = new ConsumerConfig(props);

        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
    }

    void consume() {

        Map<String, Integer> topicCountMap = new HashMap<>();

        topicCountMap.put(ServiceExecLog.TOPIC, 1);

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        MsgPackDecoder<ServiceExecLog> valueDecoder = new MsgPackDecoder<>(ServiceExecLog.class);

        Map<String, List<KafkaStream<String, ServiceExecLog>>> consumerMap =
                consumer.createMessageStreams(topicCountMap,keyDecoder, valueDecoder);

        KafkaStream<String, ServiceExecLog> stream = consumerMap.get(ServiceExecLog.TOPIC).get(0);

        for (MessageAndMetadata<String, ServiceExecLog> message : stream) {
            System.out.println(message.message());
        }

    }

    public void close() {
        consumer.shutdown();
    }

    public static void main(String[] args) {
        final KafkaConsumer c = new KafkaConsumer();
        c.consume();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                c.close();
            }
        }));
    }

}
