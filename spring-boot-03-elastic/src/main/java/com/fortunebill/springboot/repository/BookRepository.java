package com.fortunebill.springboot.repository;

import com.fortunebill.springboot.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Kavin
 * @date 2021年11月24日17:20
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

    public Book findBookByAuthorLike(String author);

}
