package com.fortunebill.springboot.bean;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Kavin
 * @date 2021年11月24日17:17
 */
@Document(indexName = "fortunebill", type = "book")
@Data
public class Book {

    private Integer id;

    private String title;

    private String author;

}
