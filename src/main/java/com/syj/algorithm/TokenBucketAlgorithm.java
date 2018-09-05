package com.syj.algorithm;

import com.syj.service.RateLimiter;
import com.syj.util.Const;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
public class TokenBucketAlgorithm implements RateLimiterAlgorithm {
    @Autowired
    private RateLimiter rateLimiter;

    public TokenBucketAlgorithm(){
        super();
        setTokenLimit();
    }
    @Override
    public void consume(String key, long limit) {
        rateLimiter.tokenConsume(key,limit);
    }
    private void setTokenLimit(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()-> rateLimiter.setTokenLimit(), 0, Const.TOKEN_BUCKET_TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }
}
