package com.xboot.mybatis.user;

import com.xboot.mybatis.core.annotation.Column;
import com.xboot.mybatis.core.annotation.Table;
import com.xboot.mybatis.core.support.entity.BaseEo;
import lombok.Data;

@Data
@Table("IUser")
public class UserEo extends BaseEo {

    @Column(value = "name")
    private String name;

    @Column(value = "department")
    private String department;

    @Column(value = "isDeleted")
    private Integer isDeleted;
}
