package com.syj.algorithm;

import com.syj.ratelimit.RateLimiter;
import com.syj.util.Const;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
@RequiredArgsConstructor
public class TokenBucketAlgorithm implements RateLimiterAlgorithm {

    @NonNull
    private RateLimiter rateLimiter;


    @Override
    public void consume(String key, long limit) {
        rateLimiter.tokenConsume(key,limit);
    }

    @PostConstruct
    private void init(){

        log.info("setTokenLimit...");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()-> rateLimiter.setTokenLimit(), 0, Const.TOKEN_BUCKET_TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }
}
