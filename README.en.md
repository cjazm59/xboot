# xboot

#Introduction to the

The Xboot scaffold, based on the Spring architecture framework, was created to simplify development

#### core

 * x-mybatis
 * x-message-api
 * x-lock-api


### x-mybatis 
based on MyBatis extension, liberate XML file, just use annotations simply, you can complete single table CRUD operation
```

@Table("user")
public class UserEo extends BaseEo {

    @Column(value = "name")
    private String name;
}

@Mapper
public interface UserMapper extends BaseMapper<UserEo, Long> {

}
```
### x-message-api
mq service
### x-lock-api
distributed lock