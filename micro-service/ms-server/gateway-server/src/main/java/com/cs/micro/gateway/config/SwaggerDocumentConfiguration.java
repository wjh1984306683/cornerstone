package com.cs.micro.gateway.config;

import com.cs.micro.gateway.support.SwaggerDocProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * swagger2 API 文档配置类
 * 本身没有访问资源，所以资源都从注册在上面的服务中来
 *
 * @author wangjiahao
 * @version 1.0
 * @className SwaggerDocumentConfiguration
 * @since 2019-02-21 17:29
 */
@Component
@Primary
public class SwaggerDocumentConfiguration implements SwaggerResourcesProvider {

    @Autowired
    private SwaggerDocProperties properties;
    /**
     * 已经注册到spring中，因为此类已被spring管理，所以会从构造器中自动注入进来，
     * 也可以删除构造器，使用@Autowired创建，效果一致
     */
    private final RouteLocator routeLocator;

    public SwaggerDocumentConfiguration(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }


    /**
     * 实现${@link SwaggerResourcesProvider}SwaggerResourcesProvide接口的方法
     * 用户获取${@link SwaggerResource}SwaggerResource资源
     */
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        System.out.println(Arrays.toString(routes.toArray()));
//        在配置文件中配置了网关代理以后，会多出一套路由地址，一个是配置后的，一个是配置前默认的，这里取默认的
        routes.stream().filter(r -> !r.getFullPath().contains(r.getId()))
                .filter(r -> properties.getName().contains(r.getId()))
                .forEach(route -> resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"), "2.0")));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
