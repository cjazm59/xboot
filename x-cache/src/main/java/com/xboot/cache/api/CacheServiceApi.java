package com.xboot.cache.api;


import com.xboot.cache.core.ICacheKey;
import com.xboot.cache.core.ICacheService;
import com.xboot.cache.core.service.impl.CacheServiceHolder;

import java.util.List;

public class CacheServiceApi implements ICacheService {

    private CacheServiceHolder cacheServiceHolder;

    public CacheServiceApi(CacheServiceHolder cacheServiceHolder) {
        this.cacheServiceHolder = cacheServiceHolder;
    }

    @Override
    public <T> T get(String key) {
        return cacheServiceHolder.get(key);
    }

    @Override
    public void set(ICacheKey cacheKey) {
        cacheServiceHolder.set(cacheKey);
    }

    @Override
    public void remove(String cacheKey) {
        cacheServiceHolder.remove(cacheKey);
    }

    @Override
    public void remove(List<String> cacheKeys) {
        cacheServiceHolder.remove(cacheKeys);
    }

    @Override
    public Boolean exists(String key) {
        return null;
    }
}
