package com.xboot.message.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class RocketMqProperties {

    @Value("${xboot.mq.group.id:}")
    private String groupId;

    @Value("${xboot.mq.consume.thread.min:1}")
    private Integer consumeThreadMin;

    @Value("${xboot.mq.consume.thread.max:50}")
    private Integer consumeThreadMax;

    @Value("${xboot.mq.consume.message.batch.maxsize:1}")
    private Integer consumeMessageBatchMaxSize;

    @Value("${xboot.mq.pull.threshold.forQueue:1000}")
    private Integer pullThresholdForQueue;

    @Value("${xboot.mq.consume.concurrently.maxSpan:500}")
    private Integer consumeConcurrentlyMaxSpan;
}
