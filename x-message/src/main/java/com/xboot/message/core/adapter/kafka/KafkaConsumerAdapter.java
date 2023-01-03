package com.xboot.message.core.adapter.kafka;



import com.xboot.message.core.IMessageHandle;
import com.xboot.message.core.MQContext;
import com.xboot.message.config.KafkaProperties;
import com.xboot.message.config.MqProperties;
import com.xboot.message.core.service.impl.AbstConsumer;
import com.xboot.message.domain.MessageDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;

public class KafkaConsumerAdapter extends AbstConsumer {

    private KafkaConsumer<String, String> kafkaConsumer;



    public KafkaConsumerAdapter(Map<String, IMessageHandle> messageHandles) {
        super(messageHandles);
    }

    @Override
    public List<MessageDTO> fetchMessages() {
        Duration timeoutDuration = Duration.ofMillis(100);
        ConsumerRecords<String, String> records = kafkaConsumer.poll(timeoutDuration);
        Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
        List<MessageDTO> result = new ArrayList<>(records.count());
        MessageDTO messageDto;
        ConsumerRecord<String, String> item;
        while (iterator.hasNext()) {
            item = iterator.next();
            messageDto = new MessageDTO(item.topic(),item.value());
            result.add(messageDto);
        }
        return result;
    }

    @Override
    public void start() {
        MqProperties mqProperties = MQContext.getMqProperties();
        KafkaProperties kafkaProp = mqProperties.getKafkaProperties();
        KafkaProperties.ConsumerProp consumerProp = kafkaProp.getConsumerProp();

        Properties props = new Properties();
        props.put("bootstrap.servers", mqProperties.getAddress());
        props.put("group.id", MQContext.getGroupName());
        //目前只适配了自动提交
        props.put("enable.auto.commit", consumerProp.getEnableAutoCommit());
        props.put("auto.commit.interval.ms", consumerProp.getAutoCommitIntervalMs());
        props.put("auto.offset.reset", consumerProp.getAutoOffsetReset());
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);

        kafkaConsumer = new KafkaConsumer<>(props);
        Set<String> messageHandles = super.messageHandles.keySet();
        if (CollectionUtils.isNotEmpty(messageHandles)) {
            kafkaConsumer.subscribe(messageHandles);
        }
        super.start();
    }

    @Override
    public void shutdown() {
        kafkaConsumer.close();
        super.shutdown();
    }

}
