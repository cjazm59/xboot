package com.xboot.message.core;


import com.xboot.message.domain.MessageDTO;
import com.xboot.message.domain.MqResult;

public interface IMessageHandle{
    MqResult process(MessageDTO message);
}
