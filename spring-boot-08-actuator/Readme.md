# 监控管理

通过引入spring-boot-starter-actuator，可以使用Spring Boot为我们提供的准生产环境下的应用监控和管理功能。我们可以通过HTTP，JMX，SSH协议来进行操作，自动得到审计、健康及指标信息等

## 步骤

1. 引入spring-boot-starter-actuator
2. 通过http方式访问监控端点
3. 可进行shutdown（POST提交，此端点默认关闭）

## 监控管理端点的描述

| 端点名      | 描述                       |
| ----------- | -------------------------- |
| autoconfig  | 所有自动装配信息           |
| auditevents | 审计事件                   |
| beans       | 所有Bean的信息             |
| configprops | 所有配置属性               |
| dump        | 线程状态信息               |
| env         | 当前环境信息               |
| health      | 应用健康状况               |
| info        | 当前应用信息               |
| metrics     | 应用的各项指标             |
| mappings    | 应用@RequestMaping映射路径 |
| shutdown    | 关闭当前应用（默认关闭）   |
| trace       | 追踪信息（最新的http请求） |
| heapdump    | 下载当前的内存dump信息     |
| loggers     | 日志信息                   |
|             |                            |

## 定制端点信息

定制端点一般通过 endpoints + 端点名 + 属性名来设置。

修改端点id （endpoints.beans.id=mybeans）

开启远程应用关闭功能（endpoints.shutdown.enabled=true）

关闭端点（endpoints.beans.enabled=false）

开启所需端点

- endpoints.enabled=false
- endpoints.beans.enabled=true

定制端点访问路径

- management.context-path=/manage

关闭http端点

- management.port=-1


