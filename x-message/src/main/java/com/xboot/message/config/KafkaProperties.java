package com.xboot.message.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class KafkaProperties {

    private ProduceProp produceProp;

    private ConsumerProp consumerProp;

    public KafkaProperties(ProduceProp produceProp, ConsumerProp consumerProp) {
        this.produceProp = produceProp;
        this.consumerProp = consumerProp;
    }

    @Data
    @Component
    public static class ProduceProp {

        @Value("${xboot.mq.produce.acks:all}")
        private String acks = "all";

        @Value("${xboot.mq.produce.retries:0}")
        private Integer retries = 0;

        @Value("${xboot.mq.produce.batch.size:16384}")
        private Integer batchSize = 16384;

        @Value("${xboot.mq.produce.linger.ms:1}")
        private Integer lingerMs = 1;

        @Value("${xboot.mq.produce.buffer.memory:33554432}")
        private Integer bufferMemory = 33554432;

    }

    @Data
    @Component
    public static class ConsumerProp {

        @Value("${xboot.mq.group.id:}")
        private String groupId;

        @Value("${xboot.mq.consumer.enable.auto.commit:true}")
        private String enableAutoCommit = "true";

        @Value("${xboot.mq.consumer.auto.commit.interval.ms:1000}")
        private Integer autoCommitIntervalMs = 1000;

        @Value("${xboot.mq.consumer.auto.offset.reset:earliest}")
        private String autoOffsetReset = "earliest";

        @Value("${xboot.mq.consumer.max.poll.records:1}")
        private Integer maxPollRecordsConfig = 1;

    }


}
