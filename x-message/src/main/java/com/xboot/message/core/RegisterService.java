package com.xboot.message.core;


import com.xboot.message.config.MqProperties;
import com.xboot.message.core.adapter.kafka.KafkaConsumerAdapter;
import com.xboot.message.core.adapter.kafka.KafkaProducerAdapter;
import com.xboot.message.core.adapter.memory.MemoryConsumerAdapter;
import com.xboot.message.core.adapter.redis.RedisConsumerAdapter;
import com.xboot.message.core.adapter.redis.RedisProducerAdapter;
import com.xboot.message.core.adapter.rocketmq.RocketMqConsumerAdapter;
import com.xboot.message.core.adapter.rocketmq.RocketMqProducerAdapter;
import com.xboot.message.core.annotation.MqConsumer;
import com.xboot.message.constant.MqType;
import com.xboot.message.core.factory.ConsumerSerivceFactory;
import com.xboot.message.core.factory.MessageHandleFactory;
import com.xboot.message.core.factory.ProducerServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.tool.utils.StringPool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

public class RegisterService implements ApplicationContextAware, InitializingBean {
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() {

		this.initContext();

		Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(MqConsumer.class);
		if (serviceMap != null || serviceMap.size() > 0) {

			for (Object serviceBean : serviceMap.values()) {
				if (serviceBean instanceof IMessageHandle) {
					MqConsumer mqConsumer = serviceBean.getClass().getAnnotation(MqConsumer.class);

					String group = mqConsumer.group();
					if (StringUtils.isBlank(group)) {
						group = MQContext.getGroupName();
					}

					StringBuffer sb = new StringBuffer(group);
					String topic = mqConsumer.topic();
					if (StringUtils.isNotBlank(topic)) {
						sb.append(StringPool.DASH).append(topic);
					}

					String tag = mqConsumer.tag();
					if (StringUtils.isNotBlank(tag)) {
						sb.append(StringPool.DASH).append(tag);
					}

					String sendType = mqConsumer.type().name();
					MessageHandleFactory.register(sb.toString(), sendType, (IMessageHandle) serviceBean);
				}
			}

		}

		StringRedisTemplate stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);

		ConsumerSerivceFactory.register(MqType.KAFKA, new KafkaConsumerAdapter(MessageHandleFactory.getMessageHandles()));
		ConsumerSerivceFactory.register(MqType.ROCKETMQ, new RocketMqConsumerAdapter(MessageHandleFactory.getMessageHandles()));
		ConsumerSerivceFactory.register(MqType.REDIS, new RedisConsumerAdapter(MessageHandleFactory.getMessageHandles(), stringRedisTemplate));

		ProducerServiceFactory.register(MqType.MEMORY, new MemoryConsumerAdapter(MessageHandleFactory.getMessageHandles()));
		ProducerServiceFactory.register(MqType.KAFKA, new KafkaProducerAdapter());
		ProducerServiceFactory.register(MqType.ROCKETMQ, new RocketMqProducerAdapter());
		ProducerServiceFactory.register(MqType.REDIS, new RedisProducerAdapter(stringRedisTemplate));


	}

	private void initContext() {
		MqProperties mqProperties = applicationContext.getBean(MqProperties.class);
		MQContext.setMqProperties(mqProperties);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
