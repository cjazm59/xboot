package com.xboot.message.core.service.impl;

import cn.hutool.json.JSONUtil;
import com.xboot.message.core.IConsumerService;
import com.xboot.message.core.IMessageHandle;
import com.xboot.message.core.factory.MessageHandleFactory;
import com.xboot.message.domain.MessageDTO;
import com.xboot.message.domain.MqResult;
import com.xboot.message.constant.SendType;
import com.xboot.message.excetion.MqExcetion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbstConsumer implements IConsumerService {
	protected Map<String, IMessageHandle> messageHandles;

	protected static ExecutorService FEITCH_MESSAGE_EXECUTOR = Executors.newFixedThreadPool(1);
	protected static ExecutorService WORKER_EXECUTOR = Executors.newWorkStealingPool();
	private AtomicBoolean closed = new AtomicBoolean(false);

	public AbstConsumer(Map<String, IMessageHandle> messageHandles) {
		this.messageHandles = messageHandles;
	}

	protected abstract List<MessageDTO> fetchMessages();

	private void doProcess(List<MessageDTO> messageList) {
		if (CollectionUtils.isEmpty(messageList)) {
			return;
		}
		for (MessageDTO messageDto : messageList) {
			Map<SendType, List<IMessageHandle>> messageHandle = this.getMessageHandle(messageDto.getTopic());
			if (messageHandle == null || messageHandle.isEmpty()) {
				return;
			}

			List<IMessageHandle> publishHandles = messageHandle.get(SendType.PUBLISH);
			if (CollectionUtils.isNotEmpty(publishHandles)) {
				this.messgeHandle(messageDto, publishHandles.get(0));
			}

			List<IMessageHandle> subscribeHandles = messageHandle.get(SendType.SUBSCRIBE);
			if (CollectionUtils.isNotEmpty(subscribeHandles)) {
				for (IMessageHandle subscribeHandle : subscribeHandles) {
					this.messgeHandle(messageDto, subscribeHandle);
				}
			}
		}
	}

	private Map<SendType, List<IMessageHandle>> getMessageHandle(String topic) {
		Map<SendType, List<IMessageHandle>> result = new HashMap<>();
		Map<String, List<IMessageHandle>> handleMap = MessageHandleFactory.get(topic);
		Set<String> keys = handleMap.keySet();
		if (CollectionUtils.isNotEmpty(keys)) {
			List<IMessageHandle> handles = new ArrayList<>();
			for (String key : keys) {
				List<IMessageHandle> messageHandles = handleMap.get(key);
				if (CollectionUtils.isEmpty(messageHandles)) {
					break;
				}
				if (key.equals(SendType.PUBLISH.name())) {
					handles.add(messageHandles.get(0));
					result.put(SendType.PUBLISH, handles);
					break;
				} else if (key.equals(SendType.SUBSCRIBE.name())) {
					for (IMessageHandle messageHandle : messageHandles) {
						handles.add(messageHandle);
					}
					result.put(SendType.SUBSCRIBE, handles);
				}
			}
		}
		return result;
	}

	protected MqResult messgeHandle(MessageDTO messageDto, IMessageHandle messageHandle) {
		MqResult mqResult;
		try {
			//这里控制做异常重试，消息日志记录，消息重复消费处理
			mqResult = messageHandle.process(messageDto);
			log.info("consume_success ===> messageDto:{} mqResult:{}", JSONUtil.toJsonStr(messageDto), JSONUtil.toJsonStr(mqResult));
		} catch (Exception e) {
			log.error("send_failed ===> messageDto:{}", JSONUtil.toJsonStr(messageDto));
			throw new MqExcetion("消息消费失败", e);
		}
		return mqResult;
	}

	protected void worker(List<MessageDTO> messages) {
		this.doProcess(messages);
	}

	protected void asyncWorker(List<MessageDTO> messages) {
		WORKER_EXECUTOR.execute(() -> {
			this.doProcess(messages);
		});
	}

	private class Worker implements Runnable {
		@Override
		public void run() {
			while (!closed.get()) {
				List<MessageDTO> messageList = fetchMessages();

				if (CollectionUtils.isNotEmpty(messageList)) {
					asyncWorker(messageList);
				} else {
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	@Override
	public void start() {
		FEITCH_MESSAGE_EXECUTOR.execute(new Worker());
	}

	@Override
	public void shutdown() {
		closed.set(true);
		FEITCH_MESSAGE_EXECUTOR.shutdown();
		WORKER_EXECUTOR.shutdown();
	}

}
