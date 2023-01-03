package com.xboot.cache.core.adapter;


import com.xboot.cache.config.CacheProperties;
import com.xboot.cache.core.service.impl.AbstCacheServiceImpl;
import com.xboot.cache.core.ICacheKey;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public class RedisCacheAdapter extends AbstCacheServiceImpl {

    private StringRedisTemplate redisTemplate;

    public RedisCacheAdapter(CacheProperties cacheProperties,StringRedisTemplate redisTemplate) {
        super(cacheProperties);
    }

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public void set(ICacheKey cacheKey) {

    }

    @Override
    public void remove(String cacheKey) {

    }

    @Override
    public void remove(List<String> cacheKeys) {

    }

}
