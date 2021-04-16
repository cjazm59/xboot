package com.xboot.mybatis.core;

import com.xboot.mybatis.util.EoUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class ProviderTemplate<E> {

    public String selectOne(E eo) {
        StringBuffer sql = new StringBuffer("select ");
        sql.append(EoUtils.getFieldsSqlStr(eo.getClass()));
        sql.append(" from " + EoUtils.getEoTableName(eo.getClass()));
        return sql.toString();
    }

    public String findById(Serializable serializable) {
        String sql = " select " + EoUtils.getFieldsSqlStr(getEntityClass()) + " from where id= " + serializable;
        return sql;
    }


    public Class getEntityClass() {
        Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }

}
