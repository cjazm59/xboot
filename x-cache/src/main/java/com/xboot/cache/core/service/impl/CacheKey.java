package com.xboot.cache.core.service.impl;

import com.xboot.cache.core.ICacheKey;

public class CacheKey implements ICacheKey {
    private String key;
    private Object value;
    private Long expires;

    public CacheKey(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public CacheKey(String key, Object value,Long expires) {
        this.key = key;
        this.value = value;
        this.expires=expires;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Long getExpires() {
        return expires;
    }

    public static class GenerateKey {
        public static String COLON = ":";

        public static String generateKey(String... keys) {
            StringBuffer sb = new StringBuffer();
            for (String key : keys) {
                sb.append(key).append(COLON);
            }
            sb.substring(1);
            return sb.toString();
        }
    }
}
