package com.syj.algorithm;

import com.syj.ratelimit.RateLimiter;
import com.syj.util.Const;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

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
@Service
@DependsOn("rateLimiter")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
public class TokenBucketAlgorithm implements RateLimiterAlgorithm {

    @NonNull
    private RateLimiter rateLimiter;


    @Override
    public void consume(String key, long limit) {
        rateLimiter.tokenConsume(key,limit);
    }

    @PostConstruct
    private void init(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()-> rateLimiter.tokenLimitIncreaseData(), 0, Const.TOKEN_BUCKET_TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }
}
