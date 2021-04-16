package com.xboot.mybatis.core;

import com.xboot.mybatis.annotation.Field;
import com.xboot.mybatis.annotation.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseEo implements Serializable {

    @Id
    private Long id;

    @Field(name = "create_time")
    private String createTime;

    @Field(name = "create_persion")
    private String createPersion;

    @Field(name = "update_time")
    private String updateTime;

    @Field(name = "update_persion")
    private String updatePersion;

    private Map<String, Object> extension = new HashMap();
}
