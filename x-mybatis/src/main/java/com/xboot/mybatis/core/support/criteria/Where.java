package com.xboot.mybatis.core.support.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Where {

    private String name;

    private Condition condition;

    private String valuel;

    public Where(String name, Condition condition, String valuel) {
        this.name = name;
        this.condition = condition;
        this.valuel = valuel;
    }
}
