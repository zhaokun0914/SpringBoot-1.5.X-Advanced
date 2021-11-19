# SpringBoot高级整合

## Cache

### 一、搭建基本环境

1. #### 导入数据库文件，创建出department和employee表

   ```sql
   -- ----------------------------
   -- Table structure for department
   -- ----------------------------
   DROP TABLE IF EXISTS `department`;
   CREATE TABLE `department`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `departmentName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
   ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
   
   -- ----------------------------
   -- Table structure for employee
   -- ----------------------------
   DROP TABLE IF EXISTS `employee`;
   CREATE TABLE `employee`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `lastName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `gender` int(2) NULL DEFAULT NULL,
     `d_id` int(11) NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
   ) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
   
   SET FOREIGN_KEY_CHECKS = 1;
   ```

2. #### 创建javaBean封装数据

   ```java
   @Data
   public class Employee {
       private Integer id;
   
       private String lastname;
   
       private String email;
   
       private Integer gender;
   
       private Integer dId;
   
   }
   ```

   ```java
   @Data
   public class Department {
       private Integer id;
   
       private String departmentname;
   }
   ```

3. #### 整合Mybatis操作数据库

    1. 配置数据源信息

       ```yml
       spring:
         datasource:
           # 数据源基本配置
           username: root
           password: 123456
           url: jdbc:mysql://192.168.5.231:3306/mybatis?useSSL=false&&useUnicode=true&&characterEncoding=utf8
           # useSSL：否则日志中会有warn警告
           # useUnicode：确认启用UTF-8编码
           # characterEncoding：如果不与数据库字符集相同，则会导致存储中文时乱码
           driver-class-name: com.mysql.jdbc.Driver
       ```

    2. 使用配置版的MyBatis

        1. 在pom文件中引入逆向工程的插件

           ```xml
           <build>
               <plugins>
                   <plugin>
                       <groupId>org.mybatis.generator</groupId>
                       <artifactId>mybatis-generator-maven-plugin</artifactId>
                       <version>1.3.2</version>
                       <dependencies>
                           <dependency>
                               <groupId>mysql</groupId>
                               <artifactId>mysql-connector-java</artifactId>
                               <version>5.1.30</version>
                           </dependency>
                       </dependencies>
                   </plugin>
               </plugins>
           </build>
           ```

        2. 创建逆向工程的配置文件 generatorConfig.xml

           ```xml
           <?xml version="1.0" encoding="UTF-8"?>
           <!DOCTYPE generatorConfiguration
                   PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
                   "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
           <generatorConfiguration>
               <context id="testTables" targetRuntime="MyBatis3">
                   <commentGenerator>
                       <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
                       <property name="suppressAllComments" value="true"/>
                   </commentGenerator>
           
                   <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
                   <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                                   connectionURL="jdbc:mysql://192.168.5.231:3306/mybatis?useSSL=false" userId="root" password="123456">
                   </jdbcConnection>
           
                   <!-- <jdbcConnection driverClass="oracle.jdbc.OracleDriver"
                   connectionURL="jdbc:oracle:thin:@127.0.0.1:1521:yycg" userId="yycg"  password="yycg">
                   </jdbcConnection> -->
                   <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
                   NUMERIC 类型解析为java.math.BigDecimal -->
                   <javaTypeResolver>
                       <property name="forceBigDecimals" value="false"/>
                   </javaTypeResolver>
           
                   <!-- targetProject:生成PO类的位置 -->
                   <javaModelGenerator targetPackage="com.fortunebill.cache.entities" targetProject="./src/main/java">
                       <!-- enableSubPackages:是否让schema作为包的后缀 -->
                       <property name="enableSubPackages" value="false"/>
                       <!-- 从数据库返回的值被清理前后的空格 -->
                       <property name="trimStrings" value="true"/>
                   </javaModelGenerator>
           
                   <!-- targetProject:mapper映射文件生成的位置 -->
                   <sqlMapGenerator targetPackage="com.fortunebill.cache.mapper" targetProject="./src/main/java">
                       <!-- enableSubPackages:是否让schema作为包的后缀 -->
                       <property name="enableSubPackages" value="false"/>
                   </sqlMapGenerator>
           
                   <!-- targetPackage：mapper接口生成的位置 -->
                   <javaClientGenerator type="XMLMAPPER" targetPackage="com.fortunebill.cache.mapper" targetProject="./src/main/java">
                       <!-- enableSubPackages:是否让schema作为包的后缀 -->
                       <property name="enableSubPackages" value="false"/>
                   </javaClientGenerator>
           
                   <!-- 指定数据库表 -->
                   <table tableName="employee"></table>
                   <table tableName="department"></table>
               </context>
           </generatorConfiguration>
           ```

        3. 使用插件生成mapper接口、mapper.xml文件、Entity实体类

        4. 指定mybatis mapper文件的路径

           ```yml
           mybatis:
             mapper-locations: classpath:mybatis/mapper/**
             # config-location: classpath:mybatis/mybatis-config.xml
             # 使用SpringBoot方式配置mybatis
             configuration:
               map-underscore-to-camel-case: true
           ```

        5. @MapperScan指定需要扫描的mapper接口所在的包

           ```java
           @SpringBootApplication
           @MapperScan("com.fortunebill.**.mapper")
           public class SpringBoot01CacheApplication {
           
               public static void main(String[] args) {
                   SpringApplication.run(SpringBoot01CacheApplication.class, args);
               }
           
           }
           ```

### 二、快速体验缓存

1. 开启基于注解的缓存`@EnableCaching`

2. 给需要缓存的方法标注缓存注解即可

    1. **@Cacheable**

       ```java
       @Override
       @Cacheable(/*cacheManager = "",*/
                  cacheNames = {"emp"},
                  /*key = "#root.methodName+'['+#id+']'",*/
                  keyGenerator = "myKeyGenerator",
                  condition = "#id>1",
                  unless = "#id==2",
                  sync = false)
       public Employee getEmployee(Integer id) {
           System.out.println("查询【" + id + "】号员工");
           return employeeMapper.selectByPrimaryKey(id);
       }
       ```

       **作用**：将方法的运行结果进行缓存，以后在想要相同的数据，直接从缓存中获取，不再调用方法

       CacheManager管理多个Cache组件，对缓存的真正CRUD操作是在具体的Cache组件中，每一个Cache组件有自己唯一一个名字

       **几个属性**：
       **cacheNames/value**：指定缓存的名字。将方法的返回值放在哪个缓存中，以数组的方式，可以指定多个缓存
       **key**：缓存数据时使用的key。默认是使用方法参数的值，例如 key:value -> 1:返回的值

       编写SpEL：#id:参数id的值 => #p0、#a0、  #root.args[0]

       getEmployee[1]

       | 名字          | 位置               | 描述                                                         | 示例                 |
                            | :------------ | :----------------- | :----------------------------------------------------------- | :------------------- |
       | methodName    | root object        | 当前被调用的方法名                                           | #root.methodName     |
       | method        | root object        | 当前被调用的方法                                             | #root.method.name    |
       | target        | root object        | 当前被调用的目标对象                                         | #root.target         |
       | targetClass   | root object        | 当前被调用的目标对象类                                       | #root.targetClass    |
       | args          | root object        | 当前被调用的方法的参数列表                                   | #root.args[0]        |
       | caches        | root object        | 当前方法调用使用的缓存列表（如@Cacheable(value={"cache1", "cache2"})，则有两个Cache） | #root.caches[0].name |
       | argument name | evaluation context | 方法参数的名字，可以直接 #参数名，也可以使用 #p0 或 #a0 的形式，0代表参数的索引 | #id、#p0、#a0        |
       | result        | evaluation context | 方法执行后的返回值（仅当方法执行之后的判断有效，如`unless`,`@CachePut`的表达式 `@CacheEvict`的表达式`beforeInvocation=false`） | #result              |

       **keyGenerator**：key的生成器，我们也可以自己指定key的生成器的组件name。**PS:key 和 keyGenerator二选一**
       **cacheManager**：指定从哪个缓存管理器中获取缓存。**PS:cacheManager 和 cacheResolver二选一**
       **cacheResolver**：缓存解析器
       **condition**：指定符合条件的情况下才**缓存**，还可以用SpEL表达式来进行判断，例如: "#id > 0"
       **unless**：指定符合条件的情况下**不缓存**
       **sync**：如果多个线程试图加载同一个键的值，则同步底层方法的调用。

       **原理**：

        1. 自动配置类

           ```java
           @Configuration
           @EnableConfigurationProperties(CacheProperties.class)
           
           @ConditionalOnClass(CacheManager.class)
           @ConditionalOnBean(CacheAspectSupport.class)
           @ConditionalOnMissingBean(value = CacheManager.class, name = "cacheResolver")
           
           @AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
           @AutoConfigureAfter({ CouchbaseAutoConfiguration.class, HazelcastAutoConfiguration.class, RedisAutoConfiguration.class })
           
           @Import(CacheConfigurationImportSelector.class)
           public class CacheAutoConfiguration {
           
               static class CacheConfigurationImportSelector implements ImportSelector {
           
                   @Override
                   public String[] selectImports(AnnotationMetadata importingClassMetadata) {
                       CacheType[] types = CacheType.values();
                       String[] imports = new String[types.length];
                       for (int i = 0; i < types.length; i++) {
                           imports[i] = CacheConfigurations.getConfigurationClass(types[i]);
                       }
                       /**
                        * 0 = "org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration"
                        * 1 = "org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration"
                        * 2 = "org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration"
                        * 3 = "org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration"
                        * 4 = "org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration"
                        * 5 = "org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration"
                        * 6 = "org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration"
                        * 7 = "org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration"
                        * 8 = "org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration"
                        * 9 = "org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration"
                        * 10 = "org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration"
                        */
                       return imports;
                   }
           
               }
           
           }
           ```

        2. 缓存的配置类

           ```
           0 = "org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration"
           1 = "org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration"
           2 = "org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration"
           3 = "org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration"
           4 = "org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration"
           5 = "org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration"
           6 = "org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration"
           7 = "org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration"
           8 = "org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration"
           9 = "org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration"
           10 = "org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration"
           ```

        3. 那些配置类能生效：`org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration`

        4. `SimpleCacheConfiguration`类给容器中注册了一个名字为`cacheManager`的`ConcurrentMapCacheManager`

        5. `ConcurrentMapCacheManager`可以获取和创建`ConcurrentMapCache`类型的`Cache`组件，将数据保存在`ConcurrentMap<Object, Object>`中

       **运行流程**

        1. 方法运行之前，先去查询`Cahce`，按照`cacheNames`指定的名字获取。（`CacheManager`先获取响应的`Cache`）

           如果`Cahce`为`null`就创建一个`ConcurrentMapCache`，放入`ConcurrentMapCacheManager.cacheMap`

        2. 去`Cache`中查找缓存的内容，使用一个`key`，默认就是方法的参数。

           key是按照某种策略生成的，默认是使用`this.metadata.keyGenerator.generate(this.target, this.metadata.method, this.args)`生成的。默认使用`SimpleKeyGenerator`生成key

           SimpleKeyGenerator生成key的策略：

           ​	如果没有参数：key = new SimpleKey();

           ​	如果有一个参数：key = 参数的值

           ​	如果有多个参数：key = new SimpleKey(params);

        3. 没有查到缓存就调用目标方法

        4. 将目标方法返回的结果放入缓存中

       @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，如果没有就运行方法并将结果放入缓存中，以后再来调用就可以直接使用缓存中的数据

       **核心**：

        1. 使用`CacheManager(ConcurrentMapCacheManager)`使用名字得到`Cahce(ConcurrentMapCache)`组件

        2. `key`使用`keyGenerator`生成，默认的`keyGenerator`是`SimpleKeyGenerator`

        3. 自定义`KeyGenerator`

           ```java
           @Configuration
           public class MyCacheConfig {
           
               @Bean
               public KeyGenerator myKeyGenerator() {
                   return (target, method, params) -> method.getName() + "(" + Arrays.asList(params) + ")";
               }
           
           }
           ```

    2. **@CachePut**

       即调用方法，又更新缓存数据，修改了数据库的某个数据，同时更新缓存

       **运行时机**：

        1. 先调用目标方法
        2. 将目标方法的结果缓存起来

       **测试步骤**：

       ```java
       @Override
       @CachePut(cacheNames = {"emp"},
                 key = "#result.id")
       public Employee updateEmployee(Employee employee) {
           System.out.println("更新【" + employee.getId() + "】号员工");
           employeeMapper.updateByPrimaryKeySelective(employee);
           return employeeMapper.selectByPrimaryKey(employee.getId());
       }
       ```

        1. 先查询1号员工，查到的结果会放入缓存中，以后查询还是同样的结果

        2. 发送请求更新员工，**PS：要注意key要相同，否则不会更新缓存中的数据**

        3. 再查询1号员工，看是否有变化

           @Cacheable的key是不能用#result.id，因为@Cacheable方法还没运行之前就要的到key，所以无法从返回结果中拿到key

    3. **@CacheEvict**

       缓存清除

       ```java
       @CacheEvict(cacheNames = {"emp"},
                   /*key = "#id",*/
                   allEntries = true)
       public String deleteEmployee(Integer id) {
           System.out.println("删除【" + id + "】号员工");
           return "success";
       }
       ```

       key：指定要清除的数据

       allEntries：清除缓存中所有的数据

       beforeInvocation：缓存的清除是否在方法之前执行，默认为false，表示方法执行之后清除

       ​	true：方法执行之前清除，即使方法执行过程中抛出异常，缓存也被删除

       ​	false：方法执行之后清除，如果在方法删除期间出错了，则不会删除缓存

    4. **@Caching**

       定义复杂的缓存规则

       ```java
       @Override
       @Caching(
               cacheable = {
                       @Cacheable(cacheNames = {"emp"}, key = "#lastName")
               },
               put = {
                       @CachePut(cacheNames = {"emp"}, key = "#result.id"),
                       @CachePut(cacheNames = {"emp"}, key = "#result.email")
               }
       )
       public Employee getEmployeeByLastName(String lastName) {
           System.out.println("查询【" + lastName + "】员工");
           EmployeeExample example = new EmployeeExample();
           example.createCriteria().andLastnameEqualTo(lastName);
           return employeeMapper.selectByExample(example).get(0);
       }
       ```

    5. **@CacheConfig**

       @CacheConfig提供了一种在类级别共享缓存相关设置的机制。

       当此注释出现在给定类上时，它为该类中定义的任何缓存操作提供一组默认设置。

       ```java
       @Service
       @CacheConfig(cacheNames = {"emp"})
       public class EmployeeServiceImpl implements EmployeeService {
       ```
