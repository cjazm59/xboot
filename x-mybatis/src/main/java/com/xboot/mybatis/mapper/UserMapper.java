package com.xboot.mybatis.mapper;

import com.xboot.mybatis.core.BaseMapper;
import com.xboot.mybatis.eo.UserEo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEo> {

    List<UserEo> findAll();
}
