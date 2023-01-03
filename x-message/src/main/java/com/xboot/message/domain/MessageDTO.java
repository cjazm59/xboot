package com.xboot.message.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class MessageDTO implements Serializable {

    //消息组，如果不指定默认是应用名称
    private String group;

    //topic
    private String topic;

    // 消息tag 用于rocketmq
    private String tag;

    //消息key,非必传
    private String key;

    //消息唯一id,如果为空自动生成
    private String msgId;

    //消息唯时间戳,如果不传自动生成
    private Date timestamp;

    public MessageDTO() {
    }

    //消息内容主体
    private Object data;

    public MessageDTO(String topic, Object data) {
        this.topic = topic;
        this.data = data;
    }

    public MessageDTO(String topic, String tag, Object data) {
        this.topic = topic;
        this.tag = tag;
        this.data = data;
    }

    public MessageDTO(String topic, String tag, String key, Object data) {
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.data = data;
    }

    public MessageDTO(Object data) {
        this.data = data;
    }

}
