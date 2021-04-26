package com.xboot.mybatis.core.support.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class Criteria {

    private List<Where> wheres;

    private OrderBy orderBy;

    private Criteria() {
    }

    public static Criteria build() {
        return new Criteria();
    }

    public Criteria andEq(String name, String value) {
        createWhere(name, Condition.EQ, value);
        return this;
    }

    public Criteria andLike(String name, String value) {
        createWhere(name, Condition.LIKE, value);
        return this;
    }

    public Criteria andGt(String name, String value) {
        createWhere(name, Condition.GT, value);
        return this;
    }

    public Criteria andLt(String name, String value) {
        createWhere(name, Condition.LT, value);
        return this;
    }

    public Criteria andNotEq(String name, String value) {
        createWhere(name, Condition.NOT_EQ, value);
        return this;
    }

    public Criteria andIn(String name, String value) {
        createWhere(name, Condition.IN, value);
        return this;
    }

    public Criteria andIn(String name, List<String> values) {
        createWhere(name, Condition.IN, StringUtils.join(values, ","));
        return this;
    }

    public Criteria andNotIn(String name, String value) {
        createWhere(name, Condition.NOT_IN, value);
        return this;
    }

    public Criteria andNotIn(String name, List<String> values) {
        createWhere(name, Condition.NOT_IN, StringUtils.join(values, ","));
        return this;
    }

    public Criteria orderByDesc(String name) {
        this.orderBy = new OrderBy(name, OrderBy.Order.DESC);
        return this;
    }

    public Criteria orderByAsc(String name) {
        this.orderBy = new OrderBy(name, OrderBy.Order.ASC);
        return this;
    }

    private void createWhere(String name, Condition condition, String value) {
        if (CollectionUtils.isEmpty(this.wheres)) {
            this.wheres = new ArrayList<>();
        }
        wheres.add(new Where(name, condition, value));
    }

}
