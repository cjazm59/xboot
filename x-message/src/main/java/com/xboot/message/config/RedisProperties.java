package com.xboot.message.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class RedisProperties {

	@Value("${xboot.mq.group.id:}")
	private String groupId;

}
