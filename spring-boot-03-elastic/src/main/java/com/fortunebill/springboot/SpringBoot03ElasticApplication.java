package com.fortunebill.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot默认支持两种技术来和elasticsearch交互
 * 1：jest
 *   需要导入JestClient的工具包(io.searchbox.client.JestClient)
 * 2：spring-data-elasticsearch【ES版本有可能不合适】
 *   版本对应 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.versions
 *   如果版本不适配
 *      1）、升级SpringBoot版本
 *      2）、安装对应版本的ES
 *   1）、Client 节点信息 ClusterNodes、ClusterName
 *   2）、ElasticsearchTemplate 操作es
 *   3）、编写一个 ElasticsearchRepository 的子接口来操作es
 *  3：两种用法
 *   1）、编写一个 ElasticsearchRepository
 *   2）、ElasticSearchTemplate（ElasticsearchTemplate的使用从4.0版本开始弃用，请使用ElasticsearchRestTemplate）
 */
@SpringBootApplication
public class SpringBoot03ElasticApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot03ElasticApplication.class, args);
    }

}
