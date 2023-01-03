package com.xboot.message.excetion;


public class MqExcetion  extends RuntimeException{

    private int code;

    public MqExcetion(String message) {
        super(message);
    }

    public MqExcetion(String message,Throwable cause) {
        super(message,cause);
    }

    public MqExcetion(int code,String message) {
        super(message);
        this.code=code;
    }


    public MqExcetion(int code,String message, Throwable cause) {
        super(message, cause);
        this.code=code;
    }
}
