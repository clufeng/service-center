package com.yonyou.mcloud.test.kafka;

import com.yonyou.mcloud.service.logger.ServiceExecLog;
import com.yonyou.mcloud.service.monitor.ServiceMonitor;
import com.yonyou.mcloud.service.util.JsonDecoder;
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
        JsonDecoder<ServiceExecLog> valueDecoder = new JsonDecoder<>(ServiceExecLog.class);

        Map<String, List<KafkaStream<String, ServiceExecLog>>> consumerMap =
                consumer.createMessageStreams(topicCountMap,keyDecoder, valueDecoder);

        KafkaStream<String, ServiceExecLog> stream = consumerMap.get(ServiceMonitor.LOG_TOPIC).get(0);

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
