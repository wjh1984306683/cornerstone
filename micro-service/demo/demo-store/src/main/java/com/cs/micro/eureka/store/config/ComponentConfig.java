package com.cs.micro.eureka.store.config;

import com.cs.base.common.SpringBootContext;
import com.cs.base.common.redis.DistributedLocker;
import com.cs.base.common.redis.RedissonDistributedLocker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 其他组件装配
 *
 * @author wangjiahao
 * @version 1.0
 * @className ComponentConfig
 * @since 2019-03-11 11:04
 */
@Configuration
public class ComponentConfig {

    @Bean
    public SpringBootContext springBootContext() {
        return new SpringBootContext();
    }

    /**
     * redisson配置系统的redis基本配置
     * 可以支持redis的各种模式，这里暂时只配置了最一般的模式
     *
     * @return
     */
    @Bean
    RedissonClient redisson() {
        return Redisson.create();
    }

    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        RedissonDistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        return locker;
    }
}
