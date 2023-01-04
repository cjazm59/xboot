package com.xboot.cache.core.adapter;


import cn.hutool.json.JSONUtil;
import com.xboot.cache.config.CacheProperties;
import com.xboot.cache.core.service.impl.AbstCacheServiceImpl;
import com.xboot.cache.core.ICacheKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public class RedisCacheAdapter extends AbstCacheServiceImpl {

    private StringRedisTemplate redisTemplate;

    private String getCacheProfix() {
        String prefix = cacheProperties.getPrefix();
        if (StringUtils.isNoneBlank(prefix)) {
            prefix = StringUtils.join(prefix, "_");
        }
        return prefix;
    }

    public RedisCacheAdapter(CacheProperties cacheProperties, StringRedisTemplate redisTemplate) {
        super(cacheProperties);
    }

    @Override
    public <T> T get(String key) {
        key = getCacheProfix() + key;
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(ICacheKey cacheKey) {
        String key = getCacheProfix() + cacheKey.getKey();
        String value = "";
        if (cacheKey.getValue() != null) {
            value = JSONUtil.toJsonStr(cacheKey.getValue());
        }
        redisTemplate.opsForValue().set(key, value, cacheKey.getExpires());
    }

    @Override
    public void remove(String cacheKey) {
        redisTemplate.delete(cacheKey);
    }

    @Override
    public void remove(List<String> cacheKeys) {
        redisTemplate.delete(cacheKeys);
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

}
