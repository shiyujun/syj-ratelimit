package com.syj.config;


import com.syj.algorithm.CounterAlgorithm;
import com.syj.algorithm.LeakyBucketAlgorithm;
import com.syj.algorithm.RateLimiterAlgorithm;
import com.syj.algorithm.TokenBucketAlgorithm;
import com.syj.ratelimit.RateLimiter;
import com.syj.ratelimit.impl.DataBaseRateLimiter;
import com.syj.ratelimit.impl.MapRateLimiter;
import com.syj.ratelimit.impl.RedisRateLimiter;
import com.syj.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

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
public class ApplicationConfiguration {



    @Bean
    public AnnotationAspect annotationAspect(){
        return new AnnotationAspect();
    }

    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "redis")
    public static class RedisConfiguration {

        @Bean
        public RateLimiter rateLimiter() {
            return new RedisRateLimiter();
        }
    }
    @ConditionalOnClass(DataSource.class)
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "sql")
    @MapperScan("com.syj.dao")
    public static class SpringDataConfiguration {
        @Bean
        public RateLimiter rateLimiter() {
            return new DataBaseRateLimiter();
        }
    }


    @ConditionalOnProperty(prefix = Const.PREFIX, name = "db", havingValue = "map", matchIfMissing = true)
    public static class MapConfiguration {
        @Bean
        public RateLimiter rateLimiter() {
            return new MapRateLimiter();
        }
    }

    @DependsOn("rateLimiter")
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "leaky")
    public static class LeakyBucketConfiguration {
        @Bean
        public RateLimiterAlgorithm rateLimiterAlgorithm() {
            return new LeakyBucketAlgorithm();
        }

    }

    @DependsOn("rateLimiter")
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
    public static class TokenBucketConfiguration {
        @Bean
        public RateLimiterAlgorithm rateLimiterAlgorithm(RateLimiter rateLimiter) {
            return new TokenBucketAlgorithm(rateLimiter);
        }
    }

    @DependsOn("rateLimiter")
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "counter", matchIfMissing = true)
    public static class CounterConfiguration {
        @Bean
        public RateLimiterAlgorithm rateLimiterAlgorithm(RateLimiter rateLimiter) {
            return new CounterAlgorithm(rateLimiter);
        }
    }
}
