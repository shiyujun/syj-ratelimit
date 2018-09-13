package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractRedisRateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
public class RedisRateLimiterTokenBucketImpl extends AbstractRedisRateLimiter {



    private DefaultRedisScript<Long> redisScript;



    public RedisRateLimiterTokenBucketImpl(DefaultRedisScript<Long> redisScript){
        this.redisScript=redisScript;

    }

    @Override
    public void tokenConsume(String key, long limit) {
        log.info("使用令牌桶算法拦截了key为{}的请求.拦截信息存储在Redis中",key);
        List<Object> keyList = new ArrayList();
        keyList.add(key);
        keyList.add(limit);
        keyList.add(Const.TOKEN_BUCKET_STEP_NUM);
        keyList.add(Const.TOKEN_BUCKET_TIME_INTERVAL);
        keyList.add(System.currentTimeMillis()/1000);
        String result=redisTemplate.execute(redisScript,keyList,keyList).toString();
        if(Const.REDIS_ERROR.contains(result)){
            throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
        }


    }

}
