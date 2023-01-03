package com.xboot.message.core.service.impl;



import cn.hutool.core.lang.UUID;
import com.xboot.message.config.MqProperties;
import com.xboot.message.constant.MqConstants;
import com.xboot.message.core.IConsumerService;
import com.xboot.message.core.IProducerService;
import com.xboot.message.core.MQContext;
import com.xboot.message.core.factory.ConsumerSerivceFactory;
import com.xboot.message.core.factory.ProducerServiceFactory;
import com.xboot.message.domain.MessageDTO;
import com.xboot.message.constant.MqType;
import com.xboot.message.excetion.MqExcetion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;

public class MqServiceHolder implements InitializingBean, DisposableBean {
    private IProducerService producerService;
    private IConsumerService consumerService;


    @Override
    public void afterPropertiesSet(){
        MqProperties mqProperties = MQContext.getMqProperties();
        MqType mqType = MqType.findByName(mqProperties.getType());
        if(mqType==null){
            throw new MqExcetion("type无效");
        }

        if(mqType!=MqType.MEMORY){
            producerService = ProducerServiceFactory.get(mqType);
            if(producerService==null){
                throw new MqExcetion(mqType.name()+" mqType未实现");
            }
            producerService.start();
        }

        consumerService = ConsumerSerivceFactory.get(mqType);
        if(consumerService==null){
            throw new MqExcetion(mqType.name()+" mqType未实现");
        }
        consumerService.start();
    }


    @Override
    public void destroy() {
        producerService.shutdown();
        consumerService.shutdown();
    }

    public MessageDTO send(MessageDTO message) {
        this.checkMessage(message);
        return producerService.send(message);
    }

    public MessageDTO asyncSend(MessageDTO message) {
        this.checkMessage(message);
        return producerService.asyncSend(message);
    }

    private void checkMessage(MessageDTO message) {
        if (StringUtils.isBlank(message.getTopic())) {
            throw new RuntimeException("topic不能为空");
        }
        if (message.getData() == null) {
            throw new RuntimeException("消息体不能为空");
        }

        if (StringUtils.isBlank(message.getGroup())) {
            message.setGroup(MQContext.getGroupName());
        }

        if(!message.getTopic().contains(message.getGroup())){
            message.setTopic(message.getGroup()+ MqConstants.StringPoll.DASH+message.getTopic());
        }


        if (StringUtils.isBlank(message.getMsgId())) {
            message.setMsgId(UUID.randomUUID().toString());
        }
        if (message.getTimestamp() == null) {
            message.setTimestamp(new Date());
        }
    }

}
