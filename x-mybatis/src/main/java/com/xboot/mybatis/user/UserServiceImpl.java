package com.xboot.mybatis.user;

import com.xboot.mybatis.core.support.service.BaseServiceImpl;
import com.xboot.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<UserEo, UserDto> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected UserMapper getMapper() {
        return userMapper;
    }
}
