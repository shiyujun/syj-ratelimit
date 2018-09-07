package com.syj.ratelimit.impl;

import com.syj.exception.BusinessErrorEnum;
import com.syj.exception.BusinessException;
import com.syj.ratelimit.abs.AbstractRedisRateLimiter;
import com.syj.util.Const;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
public class RedisRateLimiterCounterImpl extends AbstractRedisRateLimiter {



    @Override
    public void counterConsume(String key, long limit) {
        log.info("使用计数器算法拦截了key为{}的请求.拦截信息存储在Redis中",key);
        if(redisTemplate.hasKey(key)){
            Long value= Long.valueOf(redisTemplate.boundValueOps(key).get().toString());
            if(value>=limit){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }
        }
        final Long current = redisTemplate.boundValueOps(key).increment(1L);
        Long expire = redisTemplate.getExpire(key);
        if (expire == null || expire == -1) {
            redisTemplate.expire(key, Const.REFRESH_INTERVAL, SECONDS);
        }
    }
}
