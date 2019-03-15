# base-oauth2-rc oauth2's client和resource-server的组合配置依赖

用于在同一项目中，统一管理oauth权限管理

使用方法，在启动类上注解`@EnableOauth2RC`,并需要在项目中加上oauth-server的配置
```
security:
  oauth2:
    resource:
      userInfoUri: http://127.0.0.1:7500/users/current
      preferTokenInfo: false
    client:
      clientId: client
      client-secret: 123456
      access-token-uri: http://127.0.0.1:7500/oauth/token
      grant-type: client_credentials
      scope: all
```