package com.xboot.message.core;


import com.xboot.message.config.KafkaProperties;
import com.xboot.message.config.MqProperties;
import com.xboot.message.config.RedisProperties;
import com.xboot.message.config.RocketMqProperties;
import com.xboot.message.constant.MqType;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class MQContext {
	private static MQContext CONTEXT = new MQContext();
	private String groupName;
	private MqProperties mqProperties;

	private MQContext() {
	}

	public static MqProperties getMqProperties() {
		return CONTEXT.mqProperties;
	}

	public static void setMqProperties(MqProperties mqProperties) {
		if (CONTEXT.mqProperties == null) {
			CONTEXT.mqProperties = mqProperties;
		}
	}

	public static KafkaProperties getKafkaProperties() {
		return CONTEXT.mqProperties.getKafkaProperties();
	}


	public static String getGroupName() {

		if (StringUtils.isNotBlank(CONTEXT.groupName)) {
			return CONTEXT.groupName;
		}

		MqProperties mqProperties = CONTEXT.mqProperties;
		String type = mqProperties.getType();
		MqType mqType = MqType.findByName(type);

		if (mqType == MqType.KAFKA) {
			KafkaProperties kafkaProperties = mqProperties.getKafkaProperties();
			if (kafkaProperties != null
				&& StringUtils.isNotBlank(kafkaProperties.getConsumerProp().getGroupId())) {
				CONTEXT.groupName = kafkaProperties.getConsumerProp().getGroupId();
				return CONTEXT.groupName;
			}
		}

		if (mqType == MqType.ROCKETMQ) {
			RocketMqProperties rocketMqProperties = mqProperties.getRocketMqProperties();
			if (rocketMqProperties != null && StringUtils.isNotBlank(rocketMqProperties.getGroupId())) {
				CONTEXT.groupName = rocketMqProperties.getGroupId();
				return CONTEXT.groupName;
			}
		}

		if (mqType == MqType.REDIS) {
			RedisProperties redisProperties = mqProperties.getRedisProperties();
			if (redisProperties != null && StringUtils.isNotBlank(redisProperties.getGroupId())) {
				CONTEXT.groupName = redisProperties.getGroupId();
				return CONTEXT.groupName;
			}
		}

		CONTEXT.groupName = getAppName();
		return CONTEXT.groupName;
	}

	private static String getAppName() {
		Properties props = System.getProperties();
		return props.getProperty("spring.application.name", "");
	}
}
