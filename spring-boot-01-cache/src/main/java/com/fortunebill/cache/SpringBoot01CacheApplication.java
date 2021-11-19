package com.fortunebill.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 扫描所有的 mapper 接口将其纳入Spring容器中，注意不能只写**，这样会导致正常的 Spring Bean 也被 mybatis 扫描到从而出错
 *
 * @author Kavin
 * @date 2021年11月19日12:49
 */
@SpringBootApplication
@MapperScan("com.fortunebill.**.mapper")
@EnableCaching
public class SpringBoot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot01CacheApplication.class, args);
    }

}
