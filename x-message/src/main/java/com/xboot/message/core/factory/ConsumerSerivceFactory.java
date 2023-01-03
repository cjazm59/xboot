package com.xboot.message.core.factory;




import com.xboot.message.core.IConsumerService;
import com.xboot.message.constant.MqType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsumerSerivceFactory {
    private static Map<MqType, IConsumerService> SERVICE_MAP = new ConcurrentHashMap<>();

    public static void register(MqType mqType, IConsumerService consumerService) {
        SERVICE_MAP.put(mqType, consumerService);
    }

    public static void unRegister(MqType mqType) {
        SERVICE_MAP.remove(mqType);
    }

    public static IConsumerService get(MqType mqType){
        return SERVICE_MAP.get(mqType);
    }
}
