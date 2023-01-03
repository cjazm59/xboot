package com.xboot.message.core.service.impl;


import com.xboot.message.core.IProducerService;
import com.xboot.message.domain.MessageDTO;

public abstract class AbstProducer implements IProducerService {

    @Override
    public MessageDTO send(MessageDTO message) {
        return null;
    }

    @Override
    public MessageDTO asyncSend(MessageDTO message) {
        return null;
    }

    @Override
    public void start(){

    }

    @Override
    public void shutdown() {

    }

}
