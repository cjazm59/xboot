# xboot

#简介
xboot,基于spring体系框架,实现多种技术中间件适配,达到通过配置参数即可切换多种实现
#

#### 核心组件库
 * x-mybatis
 * x-message
 * x-lock
 * x-cache

### x-mybatis 
   基于mybatis实现增强,解放xml文件,只需简单使用注解,即可完成单表crud操作
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

//支持sql构造器
UserEo query = new UserEo();
Criteria criteria = Criteria.build()
        .andLike("name", "%张%")
        .andIn("id", "1,2,3");
query.setCriteria(criteria);
List<UserEo> userEos = userMapper.selectByExample(query);

```
### x-message
消息队列适配
### x-lock
分布式锁适配
### x-cahche
缓存适配