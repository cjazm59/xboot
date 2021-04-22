package com.xboot.mybatis.core.base;

import io.mybatis.provider.Caching;
import io.mybatis.provider.EntityInfoMapper;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseMapper<T extends BaseEo, P extends Serializable> extends EntityInfoMapper<T> {

    /**
     * 保存实体
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @InsertProvider(type = ProviderTemplate.class, method = "insert")
    int insert(T entity);

    /**
     * 保存实体中不为空的字段
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @InsertProvider(type = ProviderTemplate.class, method = "insertSelective")
    int insertSelective(T entity);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @DeleteProvider(type = ProviderTemplate.class, method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(P id);

    /**
     * 保存实体信息批量删除
     *
     * @param entity 实体类
     * @return 大于等于1成功，0失败
     */
    @Lang(Caching.class)
    @DeleteProvider(type = ProviderTemplate.class, method = "delete")
    int delete(T entity);

    /**
     * 根据主键更新实体
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @UpdateProvider(type = ProviderTemplate.class, method = "updateByPrimaryKey")
    int updateByPrimaryKey(T entity);

    /**
     * 根据主键更新实体中不为空的字段
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @UpdateProvider(type = ProviderTemplate.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(T entity);

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectByPrimaryKey")
    Optional<T> selectByPrimaryKey(P id);

    /**
     * 根据实体字段条件查询唯一的实体
     *
     * @param entity 实体类
     * @return 单个实体，查询结果由多条时报错
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectOne")
    Optional<T> selectOne(T entity);

    /**
     * 根据实体字段条件批量查询
     *
     * @param entity 实体类
     * @return 实体列表
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "select")
    List<T> selectList(T entity);

    /**
     * 根据实体字段条件查询总数
     *
     * @param entity 实体类
     * @return 总数
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectCount")
    long selectCount(T entity);

}

