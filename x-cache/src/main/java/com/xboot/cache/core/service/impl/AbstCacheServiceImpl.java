package com.xboot.cache.core.service.impl;


import com.xboot.cache.config.CacheProperties;
import com.xboot.cache.core.ICacheService;

public abstract class AbstCacheServiceImpl implements ICacheService {
    protected CacheProperties cacheProperties;

    public AbstCacheServiceImpl(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

}
