package com.xboot.message.api;


import com.xboot.message.core.service.impl.MqServiceHolder;
import com.xboot.message.domain.MessageDTO;

public class MessageServiceApi {

    private MqServiceHolder mqServiceHolder;

    public MessageServiceApi(MqServiceHolder mqServiceHolder) {
        this.mqServiceHolder = mqServiceHolder;
    }

    public MessageDTO send(MessageDTO message) {
        return mqServiceHolder.send(message);
    }

    public MessageDTO asyncSend(MessageDTO message) {
        return mqServiceHolder.asyncSend(message);
    }
}
