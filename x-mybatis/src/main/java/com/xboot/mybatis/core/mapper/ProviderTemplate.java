package com.xboot.mybatis.core.mapper;


import com.xboot.mybatis.core.support.entity.BaseEo;
import io.mybatis.provider.EntityColumn;
import io.mybatis.provider.EntityTable;
import io.mybatis.provider.SqlScript;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.stream.Collectors;

public class ProviderTemplate<E extends BaseEo> {

    /**
     * 不可用方法
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String unsupported(ProviderContext providerContext) {
        throw new UnsupportedOperationException(providerContext.getMapperMethod().getName() + " method not available");
    }

    /**
     * 保存实体
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String insert(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, entity -> "INSERT INTO " + entity.table()
                + "(" + entity.insertColumnList() + ")"
                + " VALUES (" + entity.insertColumns().stream()
                .map(EntityColumn::variables).collect(Collectors.joining(",")) + ")");
    }

    /**
     * 保存实体中不为空的字段
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String insertSelective(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "INSERT INTO " + entity.table()
                        + trimSuffixOverrides("(", ")", ",", () ->
                        entity.insertColumns().stream().map(column ->
                                ifTest(column.notNullTest(), () -> column.column() + ",")
                        ).collect(Collectors.joining(LF)))
                        + trimSuffixOverrides(" VALUES (", ")", ",", () ->
                        entity.insertColumns().stream().map(column ->
                                ifTest(column.notNullTest(), () -> column.variables() + ",")
                        ).collect(Collectors.joining(LF)));
            }
        });
    }

    /**
     * 根据主键删除
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String deleteByPrimaryKey(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, entity -> "DELETE FROM " + entity.table()
                + " WHERE " + entity.idColumns().stream().map(EntityColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
    }

    /**
     * 保存实体信息批量删除
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String delete(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "DELETE FROM " + entity.table()
                        + parameterNotNull("Parameter cannot be null")
                        + where(() ->
                        entity.columns().stream().map(column ->
                                ifTest(column.notNullTest(), () -> "AND " + column.columnEqualsProperty())
                        ).collect(Collectors.joining(LF)));
            }
        });
    }

    /**
     * 根据主键更新实体
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String updateByPrimaryKey(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "UPDATE " + entity.table()
                        + " SET " + entity.updateColumns().stream().map(EntityColumn::columnEqualsProperty).collect(Collectors.joining(","))
                        + where(() -> entity.idColumns().stream().map(EntityColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

    /**
     * 根据主键更新实体中不为空的字段
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String updateByPrimaryKeySelective(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "UPDATE " + entity.table()
                        + set(() ->
                        entity.updateColumns().stream().map(column ->
                                ifTest(column.notNullTest(), () -> column.columnEqualsProperty() + ",")
                        ).collect(Collectors.joining(LF)))
                        + where(() -> entity.idColumns().stream().map(EntityColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

    /**
     * 根据主键查询实体
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String selectByPrimaryKey(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.table()
                        + where(() -> entity.idColumns().stream().map(EntityColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

    /**
     * 根据实体字段条件查询唯一的实体，根据实体字段条件批量查询，查询结果的数量由方法定义
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String select(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.table()
                        + ifParameterNotNull(() ->
                        where(() ->
                                entity.whereColumns().stream().map(column ->
                                        ifTest(column.notNullTest(), () -> "AND " + column.columnEqualsProperty())
                                ).collect(Collectors.joining(LF)))
                )
                        + entity.groupByColumn().orElse("")
                        + entity.havingColumn().orElse("")
                        + entity.orderByColumn().orElse("");
            }
        });
    }

    public static String selectOne(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.table()
                        + ifParameterNotNull(() ->
                        where(() ->
                                entity.whereColumns().stream().map(column ->
                                        ifTest(column.notNullTest(), () -> "AND " + column.columnEqualsProperty())
                                ).collect(Collectors.joining(LF))) + " LIMIT 1 "
                )
                        + entity.groupByColumn().orElse("")
                        + entity.havingColumn().orElse("")
                        + entity.orderByColumn().orElse("");
            }
        });
    }


    /**
     * 根据实体字段条件查询总数
     *
     * @param providerContext 上下文
     * @return cacheKey
     */
    public static String selectCount(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "SELECT COUNT(*)  FROM " + entity.table() + LF
                        + ifParameterNotNull(() ->
                        where(() ->
                                entity.whereColumns().stream().map(column ->
                                        ifTest(column.notNullTest(), () -> "AND " + column.columnEqualsProperty())
                                ).collect(Collectors.joining(LF)))
                );
            }
        });
    }

    public static String selectByExample(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                String sql = "SELECT " + entity.baseColumnAsPropertyList() + "  FROM " + entity.table() + LF
//                        + ifParameterNotNull(() ->
//                        where(() ->
//                                entity.whereColumns().stream().map(column ->
//                                        ifTest(column.notNullTest(), () -> "AND " + column.columnEqualsProperty())
//                                ).collect(Collectors.joining(LF)))
//
//                )
                        + ifTest("criteria", () -> " and 1=1 ");
                return sql;
            }
        });
    }

}
