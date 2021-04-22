package com.xboot.mybatis.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/selectCount")
    public long selectCount() {
        UserEo u = new UserEo();
        u.setId(1L);
        long count = userMapper.selectCount(u);
        return count;
    }

    @RequestMapping("/insertSelective")
    public int insertSelective() {
        UserEo u = new UserEo();
        u.setDepartment("xxxx");
        u.setName("name");
        u.setIsDeleted(1);
        int i = userMapper.insertSelective(u);
        return i;
    }
    @RequestMapping("/selectOne")
    public UserEo selectOne() {
        UserEo u = new UserEo();
        UserEo userEo = userMapper.selectOne(u).get();
        return userEo;
    }

    @RequestMapping("/deleteByPrimaryKey")
    public void deleteByPrimaryKey(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

}
