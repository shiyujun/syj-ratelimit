package cn.org.zhixiang.ratelimit.abs;

import cn.org.zhixiang.ratelimit.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
public abstract class AbstractMapRateLimiter implements RateLimiter {

    protected  static volatile Map<String,AtomicLong> map=new ConcurrentHashMap<String, AtomicLong>();
    protected  static volatile Map<String,Long> lastPutTimeMap=new ConcurrentHashMap<String, Long>();

    @Override
    public  void counterConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) { }


    @Override
    public void tokenConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) { }




}
