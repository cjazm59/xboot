package com.xboot.cache.core.service.impl;


import com.xboot.cache.config.CacheProperties;
import com.xboot.cache.constant.CacheType;
import com.xboot.cache.core.ICacheKey;
import com.xboot.cache.core.ICacheService;
import com.xboot.cache.core.adapter.LocalCacheAdapter;
import com.xboot.cache.core.adapter.RedisCacheAdapter;
import com.xboot.cache.excetion.CacheExcetion;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;


public class CacheServiceHolder implements ICacheService, InitializingBean, ApplicationContextAware {

    private ICacheService cacheService;
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        CacheProperties cacheProp = applicationContext.getBean(CacheProperties.class);
        CacheType cacheType = CacheType.findByName(cacheProp.getType());
        if (cacheType == null) {
            throw new RuntimeException("cacheType [" + cacheProp.getType() + "] 未实现");
        }
        switch (cacheType) {
            case REDIS:
                StringRedisTemplate redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
                if (redisTemplate == null) {
                    throw new CacheExcetion("StringRedisTemplate未注入");
                }
                cacheService = new RedisCacheAdapter(cacheProp, redisTemplate);
                break;
            case LOCAL:
                cacheService = new LocalCacheAdapter(cacheProp);
                break;
            default:
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T get(String key) {
        return cacheService.get(key);
    }

    @Override
    public void set(ICacheKey cacheKey) {
        cacheService.set(cacheKey);
    }

    @Override
    public void remove(String cacheKey) {
        cacheService.remove(cacheKey);
    }

    @Override
    public void remove(List<String> cacheKeys) {
        cacheService.remove(cacheKeys);
    }

    @Override
    public Boolean exists(String key) {
        return cacheService.exists(key);
    }
}
