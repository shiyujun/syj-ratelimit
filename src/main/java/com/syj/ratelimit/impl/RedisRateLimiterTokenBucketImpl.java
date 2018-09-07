package com.syj.ratelimit.impl;

import com.syj.exception.BusinessErrorEnum;
import com.syj.exception.BusinessException;
import com.syj.ratelimit.abs.AbstractRedisRateLimiter;
import com.syj.util.Const;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
public class RedisRateLimiterTokenBucketImpl extends AbstractRedisRateLimiter {



    @Override
    public void tokenConsume(String key, long limit) {
        log.info("使用令牌桶算法拦截了key为{}的请求.拦截信息存储在Redis中",key);
        key=key+"_key";
        Long nowValue= Long.valueOf(redisTemplate.boundValueOps(key).get().toString());
        if(redisTemplate.hasKey(key)){
            if(nowValue<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                redisTemplate.boundValueOps(key).increment(-1L);
            }
        }else{
            redisTemplate.boundValueOps(key+"_limit").set(limit);
        }

    }

    @Override
    public void tokenLimitIncreaseData() {
        log.info("令牌桶增加数据");
        Set<Object> keySet= redisTemplate.keys("*_key");
        keySet.forEach(key->{
            Long nowValue= Long.valueOf(redisTemplate.boundValueOps(key).get().toString())+Const.TOKEN_BUCKET_STEP_NUM;
            Long maxValue= Long.valueOf(redisTemplate.boundValueOps(key+"_limit").get().toString());
            if(maxValue>nowValue){
                redisTemplate.boundValueOps(key).set(nowValue);
            }else {
                redisTemplate.boundValueOps(key).set(maxValue);
            }
        });
    }
}
