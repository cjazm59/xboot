package com.xboot.mybatis.core;

import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

public interface BaseMapper<E> {

    @SelectProvider(type = ProviderTemplate.class, method = "selectOne")
    E selectOne(E eo);

    @SelectProvider(type = ProviderTemplate.class, method = "findById")
    E findById(Serializable serializable);
}

