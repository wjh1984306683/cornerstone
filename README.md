# cornerstone

### config-repo:配置文件仓库
用于微服务的配置中心

### base-service:单独的项目

|项目名称|作用|版本|
|:--|:--|:--|
| base-common | 基础工具包 | 1.0-SNAPSHOT |
| cms-project | CMS独立项目 | 1.0-SNAPSHOT |
| base-oauth2-rc | oauth2的client和resource-server统一配置组件 |1.0-SNAPSHOT|
| base-security | 独立security配置组件 |1.0-SNAPSHOT|
| base-cloud | 微服务基本依赖集合 |1.0-SNAPSHOT|

说明：`base-cloud`中没有代码，只是一个依赖集合，包含以下东西，以下依赖，有一些spring boot和cloud的可以无配置直接使用，有一些其他依赖需要注解、配置或javabean配置
```
 <!--本地工具包-->
        <dependency>
            <groupId>com.cs</groupId>
            <artifactId>base-common</artifactId>
        </dependency>

        <!--微服务服务依赖包 begin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <!--spring config client动态刷新配置依赖 begin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-bus</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--spring config client动态刷新配置依赖 end-->
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!--swagger-->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
        </dependency>
        <!--分布式锁-->
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson</artifactId>
        </dependency>
        <!--微服务服务依赖包 end-->

        <!--分布式事务-->
        <dependency>
            <groupId>com.codingapi.txlcn</groupId>
            <artifactId>txlcn-tc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.codingapi.txlcn</groupId>
            <artifactId>txlcn-txmsg-netty</artifactId>
        </dependency>
```

### micro-service:微服务基础服务群

*项目环境:spring boot 2.0.3.RELEASE, spring cloud Finchley.SR1,
 jdk8+, mysql5.5+, rabbitmq, redis，lcn* 


|服务类型|项目名称|作用|版本|
|:--|:--|:--|:--|
|基础服务|eureka-server|eureka注册中心|1.0-SNAPSHOT|
|基础服务|remote-config-server|统一配置中心|1.0-SNAPSHOT|
|基础服务|gateway-server|网关|1.0-SNAPSHOT|
|基础服务|oauth2-server|授权认证中心|1.0-SNAPSHOT|
|基础服务|tx-manager|LCN分布式事务管理服务器|1.0-SNAPSHOT|
|业务服务|demo-store|商店demo|1.0-SNAPSHOT|
|业务服务|demo-customer|顾客demo|1.0-SNAPSHOT|

启动顺序以及其他注意：
1. 启动前请检查redis，mysql，rabbitmq是否启动，建议使用docker启动
2. eureka-server-->remote-config-server必须的
3. 后面可以根据依赖关系启动
4. 如果需要使用分布式事务，请务必在第一步以后，启动tx-manager

*注意*：
1. 除了`配置中心`和`eureka-server`以外，其他服务均使用配置中心的配置文件，所以他们两个一定要提前启动。
2. 在仓库中修改了配置文件以后，注意一定要提交，然后使用配置中心的`/actuator/bus-refresh`去做刷新配置的操作。
3. 配置刷新以后，如果只是普通参数，可以在`@RefreshScope`的作用刷新，如果是类似端口号一类的配置，需要重启对应服务

## 分布式事务
分布式事务使用LCN技术，需要使用的话，引入`txlcn-tc`和`txlcn-txmsg-netty`依赖，并在启动类上注解`@EnableDistributedTransaction`,这样lcn已经可以使用了。
其次，在对应事务开始的方法上加上`@LcnTransaction`才能保证此次操作使用了lcn的事务。


## 后续计划
micro-service：
- [x] 增加redis分布式锁
- [x] 增加分布式事务
- [ ] 增加定时任务
- [ ] 增加消息队列
- [ ] 增加ElasticSearch配置
- [ ] 增加链路追踪
- [ ] 增加工程全局监控
- [ ] 与docker部署结合

base-service：
- [ ] 完善base-common
- [ ] 完成cms-project工程