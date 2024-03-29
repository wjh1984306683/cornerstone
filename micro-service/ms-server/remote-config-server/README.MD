# 配置中心Remote-config-server
在微服务架构中，使用配置中心作为其他服务的配置文件统一存放管理的地方，并且可以动态在线刷新部分配置的变量

*注意*：配置中心的默认使用端口号是8888，本身这个端口号是可以更改的，但是如果修改了之后，会导致其他读取配置的客户端服务找不到配置信息，不知道原因，可能是我配置不全有一些问题，为了避免这种问题，减少成本，注意这个端口号

正常启动过程：
1. 先启动配置中心服务
2. 在其他项目中加入`bootstrap.yml`或`bootstrap.properties`文件，配置好服务中心的访问地址和配置文件名称，并启动该项目
3. 在其他项目启动的时候，会优先读取`bootstrap`文件的配置，然后去读取配置中心的配置，再做启动

动态刷新配置操作：
1. 修改对应的配置文件内容并提交
2. 在对应的服务上，或配置中心上，用POST方式执行访问`/actuator/bus-refresh`，成功之后没有返回结果
3. 再去访问已修改配置的资源

*注意*：
1. 在对应的config-client中，需要在使用了动态配置的类上注解`@RefreshScope`，否则即使修改成功了，也不会有所改变
2. 在每个项目中需要开启`/actuator/bus-refresh`这个端点，可以根据自己的需求设置端点加密
3. 在动态修改配置的项目中，会运用到消息队列和消息总线，所以需要配置一个消息队列，这里使用了rabbitmq，在这里使用到的mq，做默认配置即可
