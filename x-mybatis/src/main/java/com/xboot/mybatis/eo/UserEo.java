package com.xboot.mybatis.eo;

import com.xboot.mybatis.annotation.Field;
import com.xboot.mybatis.annotation.Table;
import com.xboot.mybatis.core.BaseEo;
import lombok.Data;

@Data
@Table(name = "IUser")
public class UserEo extends BaseEo {

    @Field(name = "name")
    private String name;

    @Field(name = "department")
    private String department;

    @Field(name = "isDeleted")
    private Integer isDeleted;
}
