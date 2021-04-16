package com.xboot.mybatis.controller;

import com.xboot.mybatis.eo.UserEo;
import com.xboot.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/selectAll")
    public UserEo selectAll() {
        UserEo users = userMapper.selectOne(new UserEo());
        return users;
    }
}
