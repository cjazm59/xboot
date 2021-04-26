package com.xboot.mybatis.mapper;

import com.xboot.mybatis.core.mapper.BaseMapper;
import com.xboot.mybatis.user.UserEo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEo, Long> {

}
