package com.fortunebill.amqp.bean;

import lombok.Data;

/**
 * @author Kavin
 * @date 2021年11月23日20:24
 */
@Data
public class Book {

    private String bookName;
    private String author;

    public Book() {
    }

    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }
}
