package com.xboot.message.core.adapter.rocketmq;


import cn.hutool.json.JSONUtil;
import com.xboot.message.core.MQContext;
import com.xboot.message.config.MqProperties;
import com.xboot.message.core.service.impl.AbstProducer;
import com.xboot.message.domain.MessageDTO;
import com.xboot.message.excetion.MqExcetion;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;


@Slf4j
public class RocketMqProducerAdapter extends AbstProducer {

	private DefaultMQProducer producer;

	@Override
	public MessageDTO send(MessageDTO message) {
		Message msg = new Message(message.getTopic(), message.getTag(), message.getKey(), JSONUtil.toJsonStr(message).getBytes());
		try {
			SendResult sendResult = producer.send(msg);
			message.setMsgId(sendResult.getMsgId());
			if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
				log.info("send_success ===> messageId:{}, group:{}, topic:{}", message.getMsgId(), message.getTopic());
			} else {
				log.error("send_failed ===> messageId:{}, group:{}, topic:{}", message.getMsgId(), message.getTopic());
			}
		} catch (Exception e) {
			log.error("发送消息异常", e);
			throw new MqExcetion("发送消息异常", e);
		}
		return message;
	}

	@Override
	public MessageDTO asyncSend(MessageDTO message) {

		Message msg = new Message(message.getTopic(), message.getTag(), message.getKey(), JSONUtil.toJsonStr(message).getBytes());
		try {
			producer.send(msg, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {
					message.setMsgId(sendResult.getMsgId());
					log.info("send_success ===> messageId:{}, group:{}, topic:{}", message.getMsgId(), message.getTopic());
				}

				@Override
				public void onException(Throwable e) {
					log.error("send_failed ===> messageId:{}, group:{}, topic:{}", message.getMsgId(), message.getTopic(), e);
				}
			});

		} catch (Exception e) {
			log.error("发送消息异常", e);
			throw new MqExcetion("发送消息异常", e);
		}
		return message;
	}

	@Override
	public void start() {
		MqProperties mqProperties = MQContext.getMqProperties();
		producer = new DefaultMQProducer(MQContext.getGroupName());
		producer.setNamesrvAddr(mqProperties.getAddress());
//		producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
		try {
			producer.start();
		} catch (Exception e) {
			throw new MqExcetion("rocketmq producer启动失败", e);
		}
		super.start();
	}

	@Override
	public void shutdown() {
		producer.shutdown();
		super.shutdown();
	}

}
