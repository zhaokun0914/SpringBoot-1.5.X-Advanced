package com.fortunebill.amqp.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kavin
 * @date 2021年11月23日20:17
 */
@Configuration
public class MyAMQPConfig {

    /**
     * 给容器中注入一个JSON MessageConverter，这样在 RabbitAutoConfiguration 配置 RabbitTemplate 的时候，
     * 会先找容器中刚才定义的 MessageConverter，这样保存在消息队列中的消息都是 JSON 格式的。
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
