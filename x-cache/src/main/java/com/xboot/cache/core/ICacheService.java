package com.xboot.cache.core;

import java.util.List;

public interface ICacheService {

    <T> T get(String cacheKey);

    void set(ICacheKey cacheKey);

    void remove(String cacheKey);

    void remove(List<String> cacheKeys);

}
