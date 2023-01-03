package com.xboot.cache.core.adapter;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xboot.cache.config.CacheProperties;
import com.xboot.cache.core.service.impl.AbstCacheServiceImpl;
import com.xboot.cache.core.ICacheKey;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class LocalCacheAdapter extends AbstCacheServiceImpl {

    private Cache<String, Object> cache;

    public LocalCacheAdapter(CacheProperties cacheProperties) {
        super(cacheProperties);
        this.cache = CacheBuilder
                .newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(3600, TimeUnit.SECONDS)
                .build();
    }


    @Override
    public <T> T get(String key) {
        ICacheKey cacheKey = (ICacheKey) cache.getIfPresent(key);
        if (cacheKey == null) {
            return null;
        }
        return (T) cacheKey.getValue();
    }

    @Override
    public void set(ICacheKey cacheKey) {
        cache.put(cacheKey.getKey(),cacheKey);
    }

    @Override
    public void remove(String cacheKey) {
        cache.invalidate(cacheKey);
    }

    @Override
    public void remove(List<String> cacheKeys) {
        if(CollectionUtil.isNotEmpty(cacheKeys)){
            for (String cacheKey : cacheKeys) {
                cache.invalidate(cacheKey);
            }
        }
    }

}
