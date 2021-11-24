# SpringBoot整合zookeeper和dubbo

## 一、安装zookeeper

```sh
[root@localhost kevin]# docker pull zookeeper:3.4.11
[root@localhost kevin]# docker run --name zookeeper -p 2181:2181 --restart always -d zookeeper:3.4.11
```

## 二、创建服务生产者和消费者

1. 创建生产者

```xml
<!-- 引入dubbo -->
<dependency>
  <groupId>com.alibaba.boot</groupId>
  <artifactId>dubbo-spring-boot-starter</artifactId>
  <version>0.1.0</version>
</dependency>

<!-- 引入zookeeper的客户端工具 -->
<dependency>
  <groupId>com.github.sgroschupf</groupId>
  <artifactId>zkclient</artifactId>
  <version>0.1</version>
</dependency>
```

```properties
# 当前dubbo服务的名字
dubbo.application.name=provider-ticket
# 注册中心的地址
dubbo.registry.address=zookeeper://192.168.1.53:2181
# 指定将哪些服务发布出去
dubbo.scan.base-packages=com.fortunebill.ticket.service
# 只要应用程序启动，dubbo就会按照 dubbo.scan.base-packages 配置的路径，
# 扫描标注了 @Service 注解的类，
# 将其注册到 dubbo.registry.address 地址指向的注册中心
```

```java
/**
 * 1、将服务提供者注册到服务中心
 *  1、引入dubbo和zookeeper相关依赖
 *  2、配置dubbo的 扫描包 和 注册中心地址
 *  3、使用@Service发布服务
 */
@SpringBootApplication
@EnableDubbo
public class ProviderTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderTicketApplication.class, args);
    }

}
```

```java

public interface TicketService {

    public String getTicket();

}

@Component  // 将其加入到spring容器中
@Service    // 将服务发布出去
public class TicketServiceImpl implements TicketService {

    @Override
    public String getTicket() {
        return "《厉害了，我的国》";
    }
}
```

2. 创建消费者

```xml
<!-- 引入dubbo -->
<dependency>
  <groupId>com.alibaba.boot</groupId>
  <artifactId>dubbo-spring-boot-starter</artifactId>
  <version>0.1.0</version>
</dependency>

<!-- 引入zookeeper的客户端工具 -->
<dependency>
  <groupId>com.github.sgroschupf</groupId>
  <artifactId>zkclient</artifactId>
  <version>0.1</version>
</dependency>
```

```properties
# 当前dubbo服务的名字
dubbo.application.name=comsumer-user
# 注册中心的地址
dubbo.registry.address=zookeeper://192.168.1.53:2181
```

```java
// 创建一个和生产者全类名相同的接口（其实正规的方法应该是引用服务者提供的接口包，这样就不用在每个消费者类路径中创建一个相同的接口了）
package com.fortunebill.ticket.service;

/**
 * 注意，类的全类名要和生产者完全相同
 */
public interface TicketService {

    public String getTicket();

}
```

```java
/**
 * 我们需要使用dubbo将我们的服务提供者注册到zookeeper中
 * 消费者需要从zookeeper中订阅服务，找到服务的提供者地址列表之后，消费者就能直接调用服务提供者的服务了
 *
 * @author Kavin
 * @date 2021年11月24日 19:45
 */
@Service
public class UserService {

    /**
     * 按照全类名匹配，看注册中心中谁注册了这个服务
     */
    @Reference
    private TicketService ticketService;

    public void hello() {
        // 我们只是调用方法，但是dubbo底层就会调用远程提供者的方法
        System.out.println("买到票了" + ticketService.getTicket());
    }

}
```


