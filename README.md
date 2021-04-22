# xboot

#简介
xboot脚手架,基于spring体系框架,为简化开发而创建
#

#### 核心组件库
 * x-mybatis
 * x-message-api
 * x-lock-api


### x-mybatis 
   基于mybatis扩展,解放xml文件,只需简单使用注解,即可完成单表crud操作
```

@Table("user")
public class UserEo extends BaseEo {

    @Column(value = "name")
    private String name;
}

//继承BaseMapper后,拥有常用crud等方法
@Mapper
public interface UserMapper extends BaseMapper<UserEo, Long> {

}
```
### x-message-api
mq消息中间件
### x-lock-api
分布式锁