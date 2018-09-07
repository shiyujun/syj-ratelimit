package com.syj.ratelimit.impl;

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
        key=key+"_key";
        Long nowValue= Long.valueOf(redisTemplate.boundValueOps(key).get().toString());
        if(redisTemplate.hasKey(key)){
            if(nowValue<=0){
                System.out.println("超出限流了，不让进了");
            }else{
                redisTemplate.boundValueOps(key).increment(-1L);
            }
        }else{
            redisTemplate.boundValueOps(key+"_limit").set(limit);
        }

    }

    @Override
    public void setTokenLimit() {
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
