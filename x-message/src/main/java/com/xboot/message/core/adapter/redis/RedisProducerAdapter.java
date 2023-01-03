package com.xboot.message.core.adapter.redis;


import cn.hutool.json.JSONUtil;
import com.xboot.message.core.service.impl.AbstProducer;
import com.xboot.message.domain.MessageDTO;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisProducerAdapter extends AbstProducer {
	private StringRedisTemplate redisTemplate;

	public RedisProducerAdapter(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public MessageDTO send(MessageDTO message) {
		redisTemplate.convertAndSend(message.getTopic(), JSONUtil.toJsonStr(message));
		return message;
	}

	@Override
	public MessageDTO asyncSend(MessageDTO message) {
		redisTemplate.convertAndSend(message.getTopic(), JSONUtil.toJsonStr(message));
		return message;
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}

}
