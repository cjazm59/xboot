package com.xboot.message.core;


import com.xboot.message.domain.MessageDTO;

public interface IProducerService {

     MessageDTO send(MessageDTO message);

     MessageDTO asyncSend(MessageDTO message);

     void start();

     void shutdown();

}
