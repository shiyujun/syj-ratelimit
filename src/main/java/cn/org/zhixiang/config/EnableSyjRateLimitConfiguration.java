package cn.org.zhixiang.config;


import cn.org.zhixiang.ratelimit.RateLimiter;
import cn.org.zhixiang.ratelimit.impl.*;
import cn.org.zhixiang.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.PropertyAccessor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.sql.DataSource;


/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/05
 * Description
 */
@Slf4j
@Configuration
@ComponentScan(basePackages="cn.org.zhixiang")
public class EnableSyjRateLimitConfiguration {


    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "rateLimiter")
    @ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
    public RateLimiter tokenRateLimiter() {
        DefaultRedisScript<Long> consumeRedisScript=new DefaultRedisScript();
        consumeRedisScript.setResultType(Long.class);
        consumeRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/redis-ratelimiter-tokenBucket.lua")));
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
