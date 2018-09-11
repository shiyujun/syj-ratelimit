package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractRedisRateLimiter;
import cn.org.zhixiang.util.Const;
import com.sun.istack.internal.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
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



    private DefaultRedisScript<Long> consumeRedisScript;

    private DefaultRedisScript<Long> increaseRedisScript;

    public RedisRateLimiterTokenBucketImpl(DefaultRedisScript<Long> consumeRedisScript,DefaultRedisScript<Long> increaseRedisScript){
        this.consumeRedisScript=consumeRedisScript;
        this.increaseRedisScript=increaseRedisScript;
    }

    @Override
    public void tokenConsume(String key, long limit) {
        log.info("使用令牌桶算法拦截了key为{}的请求.拦截信息存储在Redis中",key);
        List<Object> keyList = new ArrayList();
        keyList.add(key);
        keyList.add(limit+"");
        String result=redisTemplate.execute(consumeRedisScript,keyList,keyList).toString();
        if("-1".equals(result)){
            throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
        }


    }

    @Override
    public void tokenLimitIncreaseData() {
        log.info("令牌桶增加数据");
        List<Object> keyList = new ArrayList();
        keyList.add(Const.TOKEN_BUCKET_STEP_NUM+"");
        redisTemplate.execute(increaseRedisScript,keyList,keyList).toString();

    }
}
