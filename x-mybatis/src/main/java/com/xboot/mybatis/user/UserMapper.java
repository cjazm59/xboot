package com.xboot.mybatis.user;

import com.xboot.mybatis.core.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<UserEo, Long> {

}
