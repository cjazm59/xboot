package com.xboot.message.core.adapter.kafka;


import cn.hutool.json.JSONUtil;
import com.xboot.message.core.MQContext;
import com.xboot.message.config.KafkaProperties;
import com.xboot.message.config.MqProperties;
import com.xboot.message.core.service.impl.AbstProducer;
import com.xboot.message.domain.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Slf4j
public class KafkaProducerAdapter extends AbstProducer {
	private KafkaProducer<String, String> kafkaProducer;

	@Override
	public void start() {
		MqProperties mqProperties = MQContext.getMqProperties();
		KafkaProperties kafkaProp = mqProperties.getKafkaProperties();
		KafkaProperties.ProduceProp produceProp = kafkaProp.getProduceProp();

		Properties props = new Properties();
		props.put("bootstrap.servers", mqProperties.getAddress());
		props.put("acks", produceProp.getAcks());
		props.put("retries", produceProp.getRetries());
		props.put("batch.size", produceProp.getBatchSize());
		props.put("linger.ms", produceProp.getLingerMs());
		props.put("buffer.memory", produceProp.getBufferMemory());
		props.put("key.serializer", StringSerializer.class);
		props.put("value.serializer", StringSerializer.class);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ProducerConfig.RETRIES_CONFIG, "1");
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		kafkaProducer = new KafkaProducer(props);
	}

	@Override
	public void shutdown() {
		kafkaProducer.close();
		this.shutdown();
	}

	@Override
	public MessageDTO send(MessageDTO message) {

		kafkaProducer.send(new ProducerRecord(message.getTopic(), JSONUtil.toJsonStr(message)));
		return message;
	}

	@Override
	public MessageDTO asyncSend(MessageDTO message) {
		String group = message.getGroup();
		String topic = message.getTopic();
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, group, JSONUtil.toJsonStr(message));
		kafkaProducer.send(producerRecord, new Callback() {
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception ex) {
				if (ex == null) {
					log.info("send_success ===> group:{}, topic:{}, partition:{}, offset:{}", group, topic, recordMetadata.partition(), recordMetadata.offset());
				} else {
					log.error("send_failed ===> group:{}, topic:{}, partition:{}, offset:{}, exception:{}", group, topic, recordMetadata.partition(), recordMetadata.offset(), ex);
				}
			}
		});
		return message;
	}

}
