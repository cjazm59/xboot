package com.xboot.message.core.factory;



import com.xboot.message.core.IMessageHandle;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandleFactory  {
    private static final Map<String,Map<String, List<IMessageHandle>>> MESSAGE_HANDLES = new ConcurrentHashMap<>();
    private static final Map<String,IMessageHandle> MAPPER_MAP = new ConcurrentHashMap<>();

    public static void register(String topicName,String sendType,IMessageHandle messageHandle) {
        Map<String, List<IMessageHandle>> map = MESSAGE_HANDLES.get(topicName);
        if(map==null){
            map=new HashMap<>();
        }
        List<IMessageHandle> messageHandles = map.get(sendType);
        if(CollectionUtils.isEmpty(messageHandles)){
            messageHandles=new ArrayList<>();
        }
        messageHandles.add(messageHandle);
        map.put(sendType,messageHandles);
        MESSAGE_HANDLES.put(topicName, map);
        MAPPER_MAP.put(topicName,messageHandle);
    }

    public static void unRegister(String name) {
        MESSAGE_HANDLES.remove(name);
        MAPPER_MAP.remove(name);
    }

    public static Map<String, List<IMessageHandle>> get(String topicName) {
        return MESSAGE_HANDLES.get(topicName);
    }

    public static Map<String, IMessageHandle> getMessageHandles() {
        return MAPPER_MAP;
    }
}
