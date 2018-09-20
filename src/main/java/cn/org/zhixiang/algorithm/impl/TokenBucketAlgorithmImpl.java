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
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/05
 * Description
 */
@Service
@DependsOn("rateLimiter")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "token")
public class TokenBucketAlgorithmImpl implements RateLimiterAlgorithm {

    @NonNull
    private RateLimiter rateLimiter;


    @Override
    public void consume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) {
        rateLimiter.tokenConsume(key,limit,lrefreshInterval,tokenBucketStepNum,tokenBucketTimeInterval);
    }

}
