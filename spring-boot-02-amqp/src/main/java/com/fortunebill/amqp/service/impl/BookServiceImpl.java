package com.fortunebill.amqp.service.impl;

import com.fortunebill.amqp.bean.Book;
import com.fortunebill.amqp.service.BookService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Kavin
 * @date 2021年11月23日20:31
 */
@Service
public class BookServiceImpl implements BookService {

    @Override
    @RabbitListener(queues = {"fortunebill.news"})
    public void receive(Book book) {
        System.out.println("收到消息：" + book);
    }

    @Override
    @RabbitListener(queues = {"fortunebill"})
    public void receive02(Message message, Book book) {
        System.out.println("收到消息：" + message.getBody());
        System.out.println("收到消息：" + message.getMessageProperties());
        System.out.println("收到消息：" + book);
    }
}
