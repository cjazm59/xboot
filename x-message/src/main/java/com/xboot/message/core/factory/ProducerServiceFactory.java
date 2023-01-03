package com.xboot.message.core.factory;

import com.xboot.message.core.IProducerService;
import com.xboot.message.constant.MqType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProducerServiceFactory {
    private static Map<MqType, IProducerService> SERVICE_MAP = new ConcurrentHashMap<>();

    public static void register(MqType mqType, IProducerService producerService) {
        SERVICE_MAP.put(mqType, producerService);
    }

    public static void unRegister(MqType mqType) {
        SERVICE_MAP.remove(mqType);
    }

    public static IProducerService get(MqType mqType){
        return SERVICE_MAP.get(mqType);
    }
}
