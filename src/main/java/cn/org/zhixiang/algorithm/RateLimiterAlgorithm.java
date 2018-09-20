package cn.org.zhixiang.algorithm;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/05
 * Description   算法策略接口
 */
public interface RateLimiterAlgorithm {

    public void consume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval);
}
