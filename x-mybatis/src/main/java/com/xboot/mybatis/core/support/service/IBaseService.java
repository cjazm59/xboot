package com.xboot.mybatis.core.support.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<E, D> {

    int insert(E entity);

    int update(E entity);

    int deleteByPrimaryKey(Serializable id);

    List<D> selectByExample(E entity);

    D selectByPrimaryKey(Serializable id);

    D selectOne(E entity);

}
