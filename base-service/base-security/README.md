# base-security
是一个基本的security项目架构，已基本配置完成，项目使用了`mariadb`作为数据库，使用`lombok`减少代码量

需要自定义的部分如下所示：

在多数配置中，已加入默认配置
1. 在使用的项目中加上`@EnableBaseSecurity`注解表示开启base-security；如果不加，并且导入了此项目，表示您使用定制化的权限配置，会使用默认的spring-security的一系列配置
2. 登录页面，如若需要，自己准备一个完整的登录页面或者跳转路径，并在application配置文件中配置`base.security.loginUrl`项
3. 首页配置，和登录页面配置类似，需要在application配置文件中配置`base.security.homeUrl`
4. 登陆后返回内容类型配置，在前后分离的项目中可以使用，配置`base.security.loginType`项，有json和redirect两种
5. 不需要拦截的url和静态页面，在`base.security.permitUrls`和`base.security.permitHtml`中以列表形式添加
6. 配置`rememberMe`功能，在`base.security.rememberMe`下进行配置，默认不启用rememberMe，启用则配置`enable`为true即可，需要注意在rememberMe中有一个首次启动的配置，首次需要配置为true
7. 配置`session`相关功能，需要开启同rememberMe类似的配置，默认已开启


需要以JavaBean实现的部分：
1. 登录成功和失败处理器，可以覆盖实现
2. session过期和无效处理器，可以覆盖实现
3. UserDetail相关内容必须实现，目前使用临时用户
4. 加密模式也可以覆盖实现
5. 权限校验器需要自定义实现，需要提前设计好权限的策略

其他方面说明：
1. 本项目没有使用任何模板引擎
2. 项目中加入了`spring-boot-starter-jdbc`，只用于实现记住我功能
3. session功能没有使用其他任何数据库去处理

项目更新计划：
1. 对多种方式登录进行整合