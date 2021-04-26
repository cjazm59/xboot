package com.xboot.mybatis.core.support.entity;


import com.xboot.mybatis.core.annotation.Column;
import com.xboot.mybatis.core.support.criteria.Criteria;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseEo implements Serializable {

    @Column(value = "id")
    private Long id;

    @Column(value = "create_time")
    private String createTime;

    @Column(value = "create_persion")
    private String createPersion;

    @Column(value = "update_time")
    private String updateTime;

    @Column(value = "update_persion")
    private String updatePersion;

    protected Map<String, Object> extension = new HashMap();

    private Criteria criteria;

}
