package com.xboot.mybatis.core.support.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrderBy {

    private String order;

    public OrderBy(String order, Order orderBy) {
        this.order = order;
        this.orderBy = orderBy;
    }

    private Order orderBy;

    public enum Order {
        ASC, DESC
    }
}
