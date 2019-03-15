# demo-store
商店，eureka客户端服务的样例

加入了国际化配置，和oauth2-client的配置
国际化配置，配置类`LocaleConfiguration`，需要添加`messages.properties`的配置文件，可以在messages后面加上后缀扩展，请求上加上`lang=后缀扩展名`就可以了

oauth-store&resource-server配置：
见`base-oauth2-rc`

请求方式：
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
http://localhost:7000/test/123
请求头，注意，value=后面是一个整体
header=Authorization  value=Bearer d8b1b9c5-f92f-4ae5-a68e-b808f93f737d
```
授权中心配置见项目见`oauth2-server`

