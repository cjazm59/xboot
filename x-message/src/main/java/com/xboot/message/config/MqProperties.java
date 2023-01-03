package com.xboot.message.config;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xboot.mq")
@ConditionalOnProperty(prefix = "xboot.mq", name = "enable", havingValue = "true")
public class MqProperties {

    private String type;

    private String address;

    private Boolean enable=false;

    private KafkaProperties kafkaProperties;
    private RocketMqProperties rocketMqProperties;

	private RedisProperties redisProperties;

    public MqProperties(KafkaProperties kafkaProperties,
						RocketMqProperties rocketMqProperties,
						RedisProperties redisProperties) {
        this.kafkaProperties = kafkaProperties;
        this.rocketMqProperties = rocketMqProperties;
		this.redisProperties=redisProperties;
    }

}
