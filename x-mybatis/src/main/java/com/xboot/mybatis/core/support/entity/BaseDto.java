package com.xboot.mybatis.core.support.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseDto implements Serializable {
    protected Map<String, Object> extension = new HashMap();

}
