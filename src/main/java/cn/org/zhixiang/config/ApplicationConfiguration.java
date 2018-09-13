package cn.org.zhixiang.config;


import cn.org.zhixiang.ratelimit.impl.*;
import cn.org.zhixiang.ratelimit.RateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.sql.DataSource;


/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
@Configuration
@ComponentScan(basePackages="cn.org.zhixiang")
public class ApplicationConfiguration {

    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "redis")
    public static class RedisConfiguration {

        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
        public RateLimiter tokenRateLimiter() {
            DefaultRedisScript<Long> consumeRedisScript=new DefaultRedisScript();
            consumeRedisScript.setResultType(Long.class);
            consumeRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/redis-ratelimiter-TokenBucket-Consume.lua")));
            return new RedisRateLimiterTokenBucketImpl(consumeRedisScript);
        }

        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "counter", matchIfMissing = true)
        public RateLimiter counterRateLimiter() {
            DefaultRedisScript<Long> redisScript=new DefaultRedisScript();
            redisScript.setResultType(Long.class);
            redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/redis-ratelimiter-counter.lua")));
            return new RedisRateLimiterCounterImpl(redisScript);
        }

    }

    @ConditionalOnClass(DataSource.class)
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "sql")
    @MapperScan("com.syj.dao")
    public static class SpringDataConfiguration {

        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
        public RateLimiter tokenRateLimiter() {
            return new DataBaseRateLimiterTokenBucketImpl();
        }

        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "counter", matchIfMissing = true)
        public RateLimiter counterRateLimiter() {
            return new DataBaseRateLimiterCounterImpl();
        }
    }


    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "map", matchIfMissing = true)
    public static class MapConfiguration {
        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
        public RateLimiter tokenRateLimiter() {
            return new MapRateLimiterTokenBucketImpl();
        }

        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "counter", matchIfMissing = true)
        public RateLimiter counterRateLimiter() {
            return new MapRateLimiterCounterImpl();
        }

    }

}
