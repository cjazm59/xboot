package com.xboot.message.core.adapter.redis;


import cn.hutool.json.JSONUtil;
import com.xboot.message.core.IMessageHandle;
import com.xboot.message.core.service.impl.AbstConsumer;
import com.xboot.message.domain.MessageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisConsumerAdapter extends AbstConsumer {

	private StringRedisTemplate redisTemplate;
	private RedisMessageListenerContainer container;

	public RedisConsumerAdapter(Map<String, IMessageHandle> messageHandles,
								StringRedisTemplate redisTemplate) {
		super(messageHandles);
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void start() {
		container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisTemplate.getConnectionFactory());
		container.setSubscriptionExecutor(FEITCH_MESSAGE_EXECUTOR);
		container.setTaskExecutor(WORKER_EXECUTOR);

		Set<String> topics = messageHandles.keySet();
		MessageListenerAdapter listener;
		for (String topic : topics) {
			listener = new MessageListenerAdapter(new RedisListenerAdapter(), "onMessage");
			listener.afterPropertiesSet();
			container.addMessageListener(listener, new PatternTopic(topic));
		}

		container.start();
		super.start();
	}

	@Override
	public void shutdown() {
		container.stop();
		try {
			container.destroy();
		} catch (Exception e) {
		}
		super.shutdown();
	}

	@Override
	protected List<MessageDTO> fetchMessages() {
		return null;
	}

	public class RedisListenerAdapter {
		public void onMessage(String body) {
			if(StringUtils.isNotBlank(body)){
				MessageDTO messageDto = JSONUtil.toBean(body, MessageDTO.class);
				worker(Arrays.asList(messageDto));
			}
		}
	}

}
