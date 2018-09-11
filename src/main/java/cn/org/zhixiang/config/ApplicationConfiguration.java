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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

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




    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "redis")
    public static class RedisConfiguration {

        @Bean("redisTemplate")
        public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
            return new StringRedisTemplate(connectionFactory);
        }
        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
        public RateLimiter tokenRateLimiter() {
            return new RedisRateLimiterTokenBucketImpl();
        }

        @Bean(name = "rateLimiter")
        @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "counter", matchIfMissing = true)
        public RateLimiter counterRateLimiter() {
            return new RedisRateLimiterCounterImpl();
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
