package com.xboot.mybatis.user;

import com.xboot.mybatis.core.support.criteria.Criteria;
import com.xboot.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/selectOne")
    public List<UserEo> selectOne() {
        UserEo u = new UserEo();
        u.setId(1L);
        u.setCriteria(Criteria.build().orderByAsc("id"));
        List<UserEo> test = userMapper.selectByExample(u);
        return test;
    }

    @RequestMapping("/insert")
    public int insert() {
        UserEo u = new UserEo();
        u.setIsDeleted(1);
        u.setDepartment("xzvxcvx");
        u.setName("00012012");
        int insert = userService.insert(u);
        return insert;
    }

}
