package com.xboot.mybatis.core.support.service;

import com.xboot.mybatis.core.mapper.BaseMapper;
import com.xboot.mybatis.core.support.entity.BaseDto;
import com.xboot.mybatis.core.support.entity.BaseEo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends BaseEo, D extends BaseDto> implements IBaseService<E, D> {

    protected abstract BaseMapper getMapper();

    @Override
    public int insert(E entity) {
        return getMapper().insert(entity);
    }

    @Override
    public int update(E entity) {
        return getMapper().insert(entity);
    }

    @Override
    public int deleteByPrimaryKey(Serializable id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public D selectByPrimaryKey(Serializable id) {
        Optional optional = getMapper().selectByPrimaryKey(id);
        if (!optional.isPresent()) {
            return null;
        }
        D d = this.newInstance();
        BeanUtils.copyProperties(optional.get(), d);
        return d;
    }

    @Override
    public D selectOne(E entity) {
        Optional optional = getMapper().selectOne(entity);
        if (!optional.isPresent()) {
            return null;
        }
        D d = this.newInstance();
        BeanUtils.copyProperties(optional.get(), d);
        return d;
    }

    @Override
    public List<D> selectByExample(E entity) {
        List<E> list = getMapper().selectList(entity);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<D> r = new ArrayList<>(list.size());
        list.stream().forEach(e -> {
            D d = newInstance();
            BeanUtils.copyProperties(e, d);
            r.add(d);
        });
        return r;
    }

    private D newInstance() {
        Class clazz;
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class) type.getActualTypeArguments()[1];
        D d = null;
        try {
            d = (D) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return d;

    }
}
