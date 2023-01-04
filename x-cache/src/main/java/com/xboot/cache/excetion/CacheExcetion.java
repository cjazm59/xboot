package com.xboot.cache.excetion;


public class CacheExcetion extends RuntimeException{

    private int code;

    public CacheExcetion(String message) {
        super(message);
    }

    public CacheExcetion(String message, Throwable cause) {
        super(message,cause);
    }

    public CacheExcetion(int code, String message) {
        super(message);
        this.code=code;
    }


    public CacheExcetion(int code, String message, Throwable cause) {
        super(message, cause);
        this.code=code;
    }
}
