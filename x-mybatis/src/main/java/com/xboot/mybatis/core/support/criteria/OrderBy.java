package com.xboot.mybatis.core.support.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrderBy {

    private String order;

    private Order orderBy;

    public OrderBy(String order, Order orderBy) {
        this.order = order;
        this.orderBy = orderBy;
    }

    public enum Order {
        ASC, DESC
    }
}
