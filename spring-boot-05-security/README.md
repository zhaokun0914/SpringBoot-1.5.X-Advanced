# 1、安全

Spring Security是针对Spring项目的安全框架，也是Spring Boot底层安全模块默认的技术选型。他可以实现强大的web安全控制。对于安全控制，我们仅需引入spring-boot-starter-security模块，进行少量的配置，即可实现强大的安全管理。

几个类：

`WebSecurityConfigurerAdapter`：自定义Security策略

`AuthenticationManagerBuilder`：自定义认证策略

`@EnableWebSecurity`：开启`WebSecurity`模式

- 应用程序的两个主要区域是“认证”和“授权”（或者访问控制）。这两个主要区域是`Spring Security`的两个目标。

- “认证”（`Authentication`），是建立一个他声明的主体的过程（一个“主体”一般是指用户，设备或一些可以在你的应用程序中执行动作的其他系统）。

- “授权”（`Authorization`）指确定一个主体是否允许在你的应用程序执行一个动作的过程。为了抵达需要授权的店，主体的身份已经有认证过程建立。

- 这个概念是通用的而不只在`Spring Security`中。

# 2、Web&安全

1、登陆/注销

- `HttpSecurity`配置登陆、注销功能

2、`Thymeleaf`提供的`SpringSecurity`标签支持

- 需要引入`thymeleaf-extras-springsecurity4`
- sec:authentication="name"获得当前用户的用户名
- sec:authorize="hasRole(‘ADMIN’)"当前用户必须拥有ADMIN权限时才会显示标签内容

3、`remember me`

- 表单添加`remember-me`的checkbox
- 配置启用`remember-me`功能

4、`CSRF`（Cross-site request forgery）跨站请求伪造

- `HttpSecurity`启用csrf功能，会为表单添加_csrf的值，提交携带来预防CSRF；

