package com.xboot.mybatis.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    /**
     * 表名，默认空时使用对象名（不进行任何转换）
     */
    String value() default "";

    /**
     * 备注，仅用于在注解上展示，不用于任何其他处理
     */
    String remark() default "";

    /**
     * 使用指定的 <resultMap>
     */
    String resultMap() default "";

    /**
     * 自动根据字段生成 <resultMap>
     */
    boolean autoResultMap() default false;


}
