package com.xboot.message.constant;

public enum MqType {
    MEMORY, KAFKA,ROCKETMQ,REDIS;

    public static MqType findByName(String name) {
        MqType[] values = MqType.values();
        for (MqType value : values) {
            if (value.name().equals(name.toUpperCase())) {
                return value;
            }
        }
        return null;
    }
}
