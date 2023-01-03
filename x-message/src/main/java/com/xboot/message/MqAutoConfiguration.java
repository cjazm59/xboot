package com.xboot.message;


import com.xboot.message.api.MessageServiceApi;
import com.xboot.message.core.service.impl.MqServiceHolder;
import com.xboot.message.config.MqProperties;
import com.xboot.message.core.RegisterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnBean(value = MqProperties.class)
public class MqAutoConfiguration {

    @Bean
    public RegisterService registerService() {
        return new RegisterService();
    }

    @Bean
    public MqServiceHolder mqServiceHolder(RegisterService registerService) {
        return new MqServiceHolder();
    }

    @Bean
    @Order
    public MessageServiceApi messageServiceApi(MqServiceHolder mqServiceHolder) {
        return new MessageServiceApi(mqServiceHolder);
    }
}
