package com.fortunebill.amqp;


import com.fortunebill.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02AmqpApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {
        // amqpAdmin.declareExchange(new DirectExchange("amqpAdmin_Exchange"));
        // amqpAdmin.declareQueue(new Queue("amqpAdmin_Queue", true));
        amqpAdmin.declareBinding(new Binding("amqpAdmin_Queue",
                                             Binding.DestinationType.QUEUE,
                                             "amqpAdmin_Exchange",
                                             "amqp_routingKey",
                                             null));
        System.out.println("创建完成");
    }

    /**
     * 单播模式（点对点）
     */
    @Test
    public void test() {
        // message需要自己构造一个；定义消息体内容和消息头
        // rabbitTemplate.send(exchange, routingKey, message);

        // object默认当成消息体，只需要传入发送的对象，自动序列化保存发送给rabbitmq
        // rabbitTemplate.convertAndSend(exchange, routingKey, object)
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("Hello World", 123, false));
        // 对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange_direct",
                                      "fortunebill.news",
                                      map);

        rabbitTemplate.convertAndSend("exchange_direct",
                                      "fortunebill.news",
                                      new Book("西游记", "吴承恩"));
    }

    /**
     * 接收数据，如何将数据自动转为JSON发送出去？
     */
    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("fortunebill.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /**
     * 广播
     */
    @Test
    public void sendMsg() {
        rabbitTemplate.convertAndSend("exchange_fanout", "", new Book("红楼梦", "曹雪芹"));
    }

}
