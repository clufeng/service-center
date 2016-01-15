package com.yonyou.mcloud.test.kafka;

import com.yonyou.mcloud.service.logger.ServiceExecLog;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.DefaultDecoder;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hubo on 16/1/15
 */
public class KafkaConsumer {

    private final ConsumerConnector consumer;

    private KafkaConsumer() {
        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", "192.168.20.17:2181,192.168.20.18:2181,192.168.20.19:2181");

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
        topicCountMap.put(ServiceMonitor.LOG_TOPIC, 1);

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        DefaultDecoder valueDecoder = new DefaultDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, byte[]>>> consumerMap =
                consumer.createMessageStreams(topicCountMap,keyDecoder, valueDecoder);

        KafkaStream<String, byte[]> stream = consumerMap.get(ServiceMonitor.LOG_TOPIC).get(0);

        for (MessageAndMetadata<String, byte[]> message : stream) {

        }
    }

    public static void main(String[] args) {
        new KafkaConsumer().consume();
    }

}
