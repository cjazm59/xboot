package com.xboot.message.core.adapter.rocketmq;



import cn.hutool.json.JSONUtil;
import com.xboot.message.core.IMessageHandle;
import com.xboot.message.core.MQContext;
import com.xboot.message.config.MqProperties;
import com.xboot.message.config.RocketMqProperties;
import com.xboot.message.core.service.impl.AbstConsumer;
import com.xboot.message.domain.MessageDTO;
import com.xboot.message.excetion.MqExcetion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class RocketMqConsumerAdapter extends AbstConsumer {
    private DefaultMQPushConsumer consumer;

    public RocketMqConsumerAdapter(Map<String, IMessageHandle> messageHandles) {
        super(messageHandles);
    }

    @Override
    public void start() {

        MqProperties mqProperties = MQContext.getMqProperties();
        RocketMqProperties rocketMqProp = mqProperties.getRocketMqProperties();
        consumer = new DefaultMQPushConsumer(MQContext.getGroupName());
        consumer.setNamesrvAddr(mqProperties.getAddress());
        consumer.setConsumeMessageBatchMaxSize(rocketMqProp.getConsumeMessageBatchMaxSize());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeThreadMin(rocketMqProp.getConsumeThreadMin());
        consumer.setConsumeThreadMax(rocketMqProp.getConsumeThreadMax());
        consumer.setPullThresholdForQueue(rocketMqProp.getPullThresholdForQueue());
        consumer.setConsumeConcurrentlyMaxSpan(rocketMqProp.getConsumeConcurrentlyMaxSpan());

		Set<String> topics = messageHandles.keySet();
		if(CollectionUtils.isNotEmpty(topics)){
			for (String topic : topics) {
				try {
					consumer.subscribe(topic, "*");
				} catch (MQClientException e) {
					throw new MqExcetion("订阅失败",e);
				}
			}
			consumer.registerMessageListener(new MessageListener());
			try {
				consumer.start();
			} catch (MQClientException e) {
				throw new MqExcetion("启动失败",e);
			}

		}
        super.start();
    }


    private class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            if (msgs.isEmpty()){
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
            MessageExt msg = msgs.get(0);
            String topic = msg.getTopic();
			if (!messageHandles.containsKey(topic)) {
                log.info("未找到消费者, tipic:{}", topic);
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            try {
                byte[] body = msg.getBody();
                MessageDTO messageDto = JSONUtil.toBean(new String(body), MessageDTO.class);
                worker(Arrays.asList(messageDto));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                log.error("消息消费失败",e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }

    @Override
    public void shutdown() {
        consumer.shutdown();
        super.shutdown();
    }

    @Override
    protected List<MessageDTO> fetchMessages() {
        return null;
    }

}
