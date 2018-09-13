package cn.org.zhixiang.algorithm.impl;

import cn.org.zhixiang.algorithm.RateLimiterAlgorithm;
import cn.org.zhixiang.ratelimit.RateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

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
public class TokenBucketAlgorithmImpl implements RateLimiterAlgorithm {

    @NonNull
    private RateLimiter rateLimiter;


    @Override
    public void consume(String key, long limit) {
        rateLimiter.tokenConsume(key,limit);
    }

}
