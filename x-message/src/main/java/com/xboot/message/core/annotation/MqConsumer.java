package com.xboot.message.core.annotation;

import com.xboot.message.constant.SendType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MqConsumer {

    String group() default StringUtils.EMPTY;

    String topic();

    //如果是rocketmq 会有tag的概念
    String tag() default StringUtils.EMPTY;

    SendType type() default SendType.PUBLISH;
}
