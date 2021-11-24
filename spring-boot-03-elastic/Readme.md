# Elasticsearch

## 一、在docker中安装elasticsearch

```sh
# 下载elasticsearch
[root@kevin kevin]# docker pull elasticsearch:5.6.9

#查看IMAGE ID
[root@kevin kevin]# docker images

# 运行elasticsearch镜像
[root@kevin kevin]# docker run -e ES_JAVA_OPTS="-Xms256m -Xms2048m" --name elasticsearch -p 9200:9200 -p 9300:9300 -d elasticsearch:5.6.9

```

## 二、基本概念

```
索引 -> 数据库
类型 -> 表
文档 -> 记录
属性 -> 属性
```

## 三、增删改查

1. ### 增加(索引)记录

```http
PUT -> http://192.168.5.252:9200/megacorp/employee/1
request
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
response
{
    "_index": "megacorp",
    "_type": "employee",
    "_id": "1",
    "_version": 1,
    "result": "created",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "created": true
}
再保存一次
PUT -> http://192.168.5.252:9200/megacorp/employee/1
request
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        27,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
response
{
    "_index": "megacorp",
    "_type": "employee",
    "_id": "1",
    "_version": 2,               // 状态 + 1
    "result": "updated",         // 状态变为update
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "created": false
}
```

2. ### 检索数据

```http
GET -> http://192.168.5.252:9200/megacorp/employee/1
response
{
    "_index": "megacorp",
    "_type": "employee",
    "_id": "1",
    "_version": 1,
    "found": true,
    "_source": {
        "first_name": "John",
        "last_name": "Smith",
        "age": 25,
        "about": "I love to go rock climbing",
        "interests": [
            "sports",
            "music"
        ]
    }
}
```

3. ### 删除记录

```http
DELETE -> http://192.168.5.252:9200/megacorp/employee/3
response
{
    "found": true,
    "_index": "megacorp",
    "_type": "employee",
    "_id": "3",
    "_version": 2,
    "result": "deleted",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    }
}
再发送一边请求，会返回 not_found
response
{
    "found": false,
    "_index": "megacorp",
    "_type": "employee",
    "_id": "3",
    "_version": 1,
    "result": "not_found",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    }
}
```

4. ### 查询某员工是否存在

```http
HEAD -> http://192.168.5.252:9200/megacorp/employee/3
没有response，
如果查询到了状态是 200 OK
如果查询不到状态是 404 Not Found
```

5. ### 搜索所有员工

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search
response
{
    "took": 78,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 2,
        "max_score": 1.0,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "2",
                "_score": 1.0,
                "_source": {
                    "first_name": "Jane",
                    "last_name": "Smith",
                    "age": 32,
                    "about": "I like to collect rock albums",
                    "interests": [
                        "music"
                    ]
                }
            },
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "1",
                "_score": 1.0,
                "_source": {
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 27,
                    "about": "I love to go rock climbing",
                    "interests": [
                        "sports",
                        "music"
                    ]
                }
            }
        ]
    }
}
```

6. ### 查询指定last_name的员工

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search?q=last_name:Smith
response
{
    "took": 15,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 2,
        "max_score": 0.2876821,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "2",
                "_score": 0.2876821,
                "_source": {
                    "first_name": "Jane",
                    "last_name": "Smith",
                    "age": 32,
                    "about": "I like to collect rock albums",
                    "interests": [
                        "music"
                    ]
                }
            },
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "1",
                "_score": 0.2876821,
                "_source": {
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 27,
                    "about": "I love to go rock climbing",
                    "interests": [
                        "sports",
                        "music"
                    ]
                }
            }
        ]
    }
}
```

7. ### 使用查询表达式搜索

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search
request
{
    "query" : {
        "match" : {
            "last_name" : "Smith"
        }
    }
}
response
{
    "took": 4,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 2,
        "max_score": 0.2876821,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "2",
                "_score": 0.2876821,
                "_source": {
                    "first_name": "Jane",
                    "last_name": "Smith",
                    "age": 32,
                    "about": "I like to collect rock albums",
                    "interests": [
                        "music"
                    ]
                }
            },
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "1",
                "_score": 0.2876821,
                "_source": {
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 27,
                    "about": "I love to go rock climbing",
                    "interests": [
                        "sports",
                        "music"
                    ]
                }
            }
        ]
    }
}
```

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search
request
{
    "query" : {
        "bool": {
            "must": {
                "match" : {
                    "last_name" : "smith"    // 这部分与我们之前使用的 match 查询 一样。
                }
            },
            "filter": {
                "range" : {
                    "age" : { "gt" : 30 }    // 这部分是一个 range 过滤器，它能找到年龄大于 30 的文档，其中 gt 表示_大于_(great than)。
                }
            }
        }
    }
}
response
{
    "took": 3,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 1,
        "max_score": 0.2876821,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "2",
                "_score": 0.2876821,
                "_source": {
                    "first_name": "Jane",
                    "last_name": "Smith",
                    "age": 32,
                    "about": "I like to collect rock albums",
                    "interests": [
                        "music"
                    ]
                }
            }
        ]
    }
}
```

8. ### 全文检索

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search
request
{
    "query" : {
        "match" : {
            "about" : "rock climbing" // 只要包含该字段就查询出来
        }
    }
}
response
{
    "took": 6,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 2,
        "max_score": 0.53484553,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "1",
                "_score": 0.53484553, // 相关性得分
                "_source": {
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 27,
                    "about": "I love to go rock climbing",
                    "interests": [
                        "sports",
                        "music"
                    ]
                }
            },
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "2",
                "_score": 0.26742277, // 相关性得分
                "_source": {
                    "first_name": "Jane",
                    "last_name": "Smith",
                    "age": 32,
                    "about": "I like to collect rock albums",
                    "interests": [
                        "music"
                    ]
                }
            }
        ]
    }
}
```

9. ### 短语匹配

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search
request
{
    "query" : {
        "match_phrase" : {  // 将 match 改成 match_phrase 即可进行短语匹配
            "about" : "rock climbing"
        }
    }
}
response
{
    "took": 12,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 1,
        "max_score": 0.53484553,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "1",
                "_score": 0.53484553,
                "_source": {
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 27,
                    "about": "I love to go rock climbing",
                    "interests": [
                        "sports",
                        "music"
                    ]
                }
            }
        ]
    }
}
```

10. ### 高亮搜索

```http
GET -> http://192.168.5.252:9200/megacorp/employee/_search
request
{
    "query" : {
        "match_phrase" : {
            "about" : "rock climbing"
        }
    },
    "highlight": {
        "fields" : {
            "about" : {}
        }
    }
}
response
{
    "took": 34,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 1,
        "max_score": 0.53484553,
        "hits": [
            {
                "_index": "megacorp",
                "_type": "employee",
                "_id": "1",
                "_score": 0.53484553,
                "_source": {
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 27,
                    "about": "I love to go rock climbing",
                    "interests": [
                        "sports",
                        "music"
                    ]
                },
                "highlight": {
                    "about": [
                        "I love to go <em>rock</em> <em>climbing</em>"   // 原始文本中的高亮片段
                    ]
                }
            }
        ]
    }
}
```

## 四、SpringBoot整合Elasticsearch

见源代码
