package com.fortunebill.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Kavin
 * @date 2021年11月19日14:52
 */
@Configuration
public class MyCacheConfig {

    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> method.getName() + "(" + Arrays.asList(params) + ")";
    }

}
