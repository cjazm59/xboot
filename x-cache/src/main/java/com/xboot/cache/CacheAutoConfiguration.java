package com.xboot.cache;

import com.xboot.cache.api.CacheServiceApi;
import com.xboot.cache.config.CacheProperties;
import com.xboot.cache.core.service.impl.CacheServiceHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(value = CacheProperties.class)
public class CacheAutoConfiguration {

    @Bean
    public CacheServiceHolder cacheServiceHolder(){
        return new CacheServiceHolder();
    }

    @Bean
    public CacheServiceApi cacheServiceApi(CacheServiceHolder cacheServiceHolder){
        return new CacheServiceApi(cacheServiceHolder);
    }
}
