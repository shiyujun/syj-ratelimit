package com.syj.algorithm;

import com.syj.ratelimit.RateLimiter;
import com.syj.ratelimit.impl.DataBaseRateLimiter;
import com.syj.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    final static Logger log = LoggerFactory.getLogger(TokenBucketAlgorithm.class);
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
        log.info("setTokenLimit...");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()-> rateLimiter.setTokenLimit(), 0, Const.TOKEN_BUCKET_TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }
}
