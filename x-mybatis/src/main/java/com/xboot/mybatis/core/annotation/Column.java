package com.xboot.mybatis.core.annotation;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 列名，默认空时使用字段名（不进行任何转换）
     */
    String value() default "";

    /**
     * 备注，仅用于在注解上展示，不用于任何其他处理
     */
    String remark() default "";

    /**
     * 标记字段是否为主键字段
     */
    boolean id() default false;

    /**
     * 排序方式，默认空时不作为排序字段，只有手动设置 ASC 和 DESC 才有效
     */
    String orderBy() default "";

    /**
     * 可查询
     */
    boolean selectable() default true;

    /**
     * 可插入
     */
    boolean insertable() default true;

    /**
     * 可更新
     */
    boolean updatable() default true;

    /**
     * 数据库类型 {, jdbcType=VARCHAR}
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * 类型处理器 {, typeHandler=XXTypeHandler}
     */
    Class<? extends TypeHandler> typeHandler() default UnknownTypeHandler.class;

    /**
     * 小数位数 {, numericScale=2}
     */
    String numericScale() default "";

}
