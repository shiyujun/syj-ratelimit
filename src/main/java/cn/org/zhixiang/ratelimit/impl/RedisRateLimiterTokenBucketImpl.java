package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.RateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/05
 * Description
 */
@Slf4j
public class RedisRateLimiterTokenBucketImpl extends RateLimiter {

    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> redisScript;



    public RedisRateLimiterTokenBucketImpl(DefaultRedisScript<Long> redisScript){
        this.redisScript=redisScript;

    }

    @Override
    public void tokenConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) {

        List<Object> keyList = new ArrayList();
        keyList.add(key);
        keyList.add(limit+"");
        keyList.add(tokenBucketStepNum+"");
        keyList.add(tokenBucketTimeInterval+"");
        keyList.add(System.currentTimeMillis()/1000+"");
        String result=redisTemplate.execute(redisScript,keyList,keyList).toString();
        if(Const.REDIS_ERROR.equals(result)){
            throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
        }

    }

}
