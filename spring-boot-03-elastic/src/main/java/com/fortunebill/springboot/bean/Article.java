package com.fortunebill.springboot.bean;

import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * @author Kavin
 * @date 2021年11月24日15:58
 */
@Data
public class Article {

    @JestId
    private Integer id;

    private String author;

    private String title;

    private String context;
}
