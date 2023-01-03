package com.xboot.cache.core;

public interface ICacheKey {
    String getKey();

    Object getValue();

    Long getExpires();

}
