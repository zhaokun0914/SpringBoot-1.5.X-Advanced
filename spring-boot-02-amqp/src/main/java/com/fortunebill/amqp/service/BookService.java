package com.fortunebill.amqp.service;

import com.fortunebill.amqp.bean.Book;
import org.springframework.amqp.core.Message;

/**
 * @author Kavin
 * @date 2021年11月23日20:30
 */
public interface BookService {

    public void receive(Book book);

    public void receive02(Message message, Book book);
}
