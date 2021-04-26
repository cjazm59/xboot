package com.xboot.mybatis.core.mapper;

import com.xboot.mybatis.core.support.entity.BaseEo;
import io.mybatis.provider.Caching;
import io.mybatis.provider.EntityInfoMapper;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseMapper<E extends BaseEo, P extends Serializable> extends EntityInfoMapper<E> {

    /**
     * 保存实体
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @InsertProvider(type = ProviderTemplate.class, method = "insert")
    int insert(E entity);

    /**
     * 保存实体中不为空的字段
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @InsertProvider(type = ProviderTemplate.class, method = "insertSelective")
    int insertSelective(E entity);

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
    int delete(E entity);

    /**
     * 根据主键更新实体
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @UpdateProvider(type = ProviderTemplate.class, method = "updateByPrimaryKey")
    int updateByPrimaryKey(E entity);

    /**
     * 根据主键更新实体中不为空的字段
     *
     * @param entity 实体类
     * @return 1成功，0失败
     */
    @Lang(Caching.class)
    @UpdateProvider(type = ProviderTemplate.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(E entity);

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectByPrimaryKey")
    Optional<E> selectByPrimaryKey(P id);

    /**
     * 根据实体字段条件查询唯一的实体
     *
     * @param entity 实体类
     * @return 单个实体，查询结果由多条时报错
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectOne")
    Optional<E> selectOne(E entity);

    /**
     * 根据实体字段条件批量查询
     *
     * @param entity 实体类
     * @return 实体列表
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "select")
    List<E> selectList(E entity);

    /**
     * 根据实体字段条件查询总数
     *
     * @param entity 实体类
     * @return 总数
     */
    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectCount")
    long selectCount(E entity);


    @Lang(Caching.class)
    @SelectProvider(type = ProviderTemplate.class, method = "selectByExample")
    List<E> selectByExample(E entity);


}

