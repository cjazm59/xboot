package com.xboot.message.core.adapter.memory;


import com.xboot.message.core.IMessageHandle;
import com.xboot.message.core.IProducerService;
import com.xboot.message.core.service.impl.AbstConsumer;
import com.xboot.message.domain.MessageDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MemoryConsumerAdapter extends AbstConsumer implements IProducerService {

    public MemoryConsumerAdapter(Map<String, IMessageHandle> messageHandles) {
        super(messageHandles);
    }

    @Override
    public List<MessageDTO> fetchMessages() {
        return null;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public MessageDTO send(MessageDTO message) {
        super.worker(Arrays.asList(message));
        return message;
    }

    @Override
    public MessageDTO asyncSend(MessageDTO message) {
        super.asyncWorker(Arrays.asList(message));
        return message;
    }
}
