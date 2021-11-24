package com.fortunebill.springboot;

import com.fortunebill.springboot.bean.Article;
import com.fortunebill.springboot.bean.Book;
import com.fortunebill.springboot.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot03ElasticApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void test01() {
        /*Book book = new Book();
        book.setId(1);
        book.setTitle("哈利波特");
        book.setAuthor("J·K 罗琳");
        bookRepository.save(book);*/

        /*for (Book book : bookRepository.findAll()) {
            System.out.println(book);
        }*/

        /*Book k = bookRepository.findOne(1);
        System.out.println(k);*/

        Book book = bookRepository.findBookByAuthorLike("J·K");
        System.out.println(book);

    }

    /**
     * GET -> http://192.168.5.252:9200/fortunebill/news/1
     * response
     * {
     *     "_index": "fortunebill",
     *     "_type": "news",
     *     "_id": "1",
     *     "_version": 1,
     *     "found": true,
     *     "_source": {
     *         "id": 1,
     *         "author": "张三",
     *         "title": "好消息",
     *         "context": "Hello World"
     *     }
     * }
     */
    @Test
    public void test() {
        // 1. 给es中索引(保存)一个文档
        Article article = new Article();
        article.setId(1);
        article.setAuthor("张三");
        article.setTitle("好消息");
        article.setContext("Hello World");

        // 2. 构建一个索引功能
        Index index = new Index.Builder(article).index("fortunebill").type("news").build();
        try {
            // 执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search() {
        // 查询表达式
        String query = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"context\" : \"Hello\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // 构建搜索功能
        Search search = new Search.Builder(query).addIndex("fortunebill").addType("news").build();
        try {
            SearchResult execute = jestClient.execute(search);
            System.out.println(execute.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
