package com.fortunebill.amqp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自动配置
 * 1、RabbitAutoConfiguration
 * 2、自动配置了连接工厂CachingConnectionFactory
 * 3、RabbitProperties 封装了rabbitmq的所有配置
 * 4、RabbitTemplate：给rabbitmq发送和接收消息
 * 5、AmqpAdmin：是rabbitmq的系统管理组件，创建声明队列、交换器等
 *    AmqpAdmin 创建和删除 Queue Exchange Binding
 * 6、@EnableRabbit + @RabbitListener 监听消息队列的内容
 */
@SpringBootApplication
@EnableRabbit // 开启基于注解的MQ
public class SpringBoot02AmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot02AmqpApplication.class, args);
    }

}
