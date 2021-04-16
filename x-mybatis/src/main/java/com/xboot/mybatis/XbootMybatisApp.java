package com.xboot.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xboot.mybatis.mapper")
public class XbootMybatisApp {
    public static void main(String[] args) {
        SpringApplication.run(XbootMybatisApp.class, args);
    }
}
