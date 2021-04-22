package com.xboot.mybatis.core.base;


import com.xboot.mybatis.core.annotation.Column;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePersion() {
        return createPersion;
    }

    public void setCreatePersion(String createPersion) {
        this.createPersion = createPersion;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePersion() {
        return updatePersion;
    }

    public void setUpdatePersion(String updatePersion) {
        this.updatePersion = updatePersion;
    }

    public Map<String, Object> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, Object> extension) {
        this.extension = extension;
    }


    private Map<String, Object> extension = new HashMap();
}
