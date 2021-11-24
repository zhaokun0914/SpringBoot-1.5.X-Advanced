package com.fortunebill.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、引入依赖
 * 2、配置dubbo的注册中心地址
 * 3、引用服务
 */
@SpringBootApplication
public class ComsumerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComsumerUserApplication.class, args);
    }

}
