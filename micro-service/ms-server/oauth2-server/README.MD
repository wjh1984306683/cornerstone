# Oauth2.0授权中心 Oauth2-server
微服务项目中，使用了Oauth2.0作为项目的权限管理。
这是一个鉴权中心，也包含了基本的用户信息，所有使用权限的服务模块，都需要从这里获取权限

用户信息方面只是是实现了一个最简单的`用户-角色-权限`的三方关系，具体实现可以根据需求和实际去是实现。
因此它本身也是一个资源服务器。

关键信息详述：

1. 项目中的核心配置：`Oauth2Configuration`和`SecurityConfiguration`两个类，1是对授权中心的配置，2是对此项目的基本权限控制。
*有个地方需要注意，此项目虽然也是资源服务器，但是在这点上，并没有特别的地方，所以只需要在启动类上加上`@EnableResourceServer`，使用最普通的默认配置既可。*

2. 本项目使用了redis去存储token，所以需要实现redis的相关代码，这里spring-cloud-oauth2带来的相关jar中，有一个地方非常重要也不容易被注意到，必须结合redis自定义实现`TokenStore`这个接口，详见`MyRedisTokenStore`类

3. 项目里还需要对外暴露一个接口地址，`UserCtrl`类中的`/users/current`，表示在鉴权成功之后，可以在这里获得当前的权限信息，是必须的。

4. 项目中目前使用到了数据库和jpa的代码去实现了用户信息的部分，这里可以使用其他方式实现

5. 在`Oauth2Configuration`中，这里使用了在内存中记录了两个客户端资源的信息，这些信息的必须要和客户端的对应上，才可以获得正确的权限去访问资源，请不要乱写，这里必须要使用一个加密配置，我们使用spring-security的`BCryptPasswordEncoder`既可
```
 @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = new BCryptPasswordEncoder().encode("123456");

        // 配置两个客户端，一个用于password认证一个用于client认证
        clients.inMemory()
                .withClient("client_1") //客户端名称，可随意填写
                .resourceIds("user")    //资源ID，可以随填写
                .authorizedGrantTypes("client_credentials","password", "refresh_token") //授权模式，只有这几种方式，可以全选，少选，不能不选
                .scopes("all")          //授权范围，可随意填写，但请注意可能和你自己的基本权限结合使用
                .authorities("oauth2")  //表示oauth2的角色，类似security的authority
                .secret(finalSecret)    //客户端密码，需要用加密器加密
                .and()
                .withClient("client_2")
                .resourceIds("user")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(finalSecret);
    }

```

请求方式，如果没有使用postman这类工具的话，请使用最普通的方式，以password模式举例
1. 请求token，会返回结果
```
请求路径：
http://localhost:7500/oauth/token?client_id=client_1&client_secret=123456&grant_type=password&username=admin&password=123

返回结果：
{
    "access_token": "d8b1b9c5-f92f-4ae5-a68e-b808f93f737d",
    "token_type": "bearer",
    "refresh_token": "bb805e66-723d-456c-b85e-221b1cd19d0f",
    "expires_in": 43199,
    "scope": "all"
}
```
2. 在请求头里加上token，访问内容即可
```
请求路径：
http://localhost:7500/users/current
请求头，注意，value=后面是一个整体
header=Authorization  value=Bearer d8b1b9c5-f92f-4ae5-a68e-b808f93f737d
```

客户端配置见项目`demo-store`

#### 首次运行，启动后，执行`init-data.sql`文件，注意插入数据的顺序。
