package com.xboot.cache.constant;

public enum CacheType {
    REDIS,LOCAL;
    public static CacheType findByName(String name) {
        CacheType[] values = CacheType.values();
        for (CacheType value : values) {
            if (value.name().equals(name.toUpperCase())) {
                return value;
            }
        }
        return null;
    }

}
