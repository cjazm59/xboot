package com.xboot.mybatis.user;

import com.xboot.mybatis.core.support.entity.BaseDto;
import lombok.Data;

@Data
public class UserDto extends BaseDto {
    
    private Long id;

    private String name;

    private String department;

    private Integer isDeleted;

}
