package com.xboot.message.domain;


import com.xboot.message.constant.MqConstants;

public class MqResult {


    private int code;
    private int msg;

    public MqResult(int code) {
        this.code = code;
    }

    public Boolean isSucceed(){
        if(code==MqConstants.MQ_RESULT_SUCCEED_CODE){
            return true;
        }
        return false;
    }

    public static MqResult succeed(){
        return new MqResult(MqConstants.MQ_RESULT_SUCCEED_CODE);
    }

    public static MqResult fail(){
        return new MqResult(MqConstants.MQ_RESULT_FAIL_CODE);
    }

}
