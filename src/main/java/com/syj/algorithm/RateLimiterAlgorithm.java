package com.syj.algorithm;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 算法策略接口
 */
public interface RateLimiterAlgorithm {

    public void consume(String key, long limit);
}
