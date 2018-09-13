package cn.org.zhixiang.ratelimit.abs;

import cn.org.zhixiang.ratelimit.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
public abstract class AbstractMapRateLimiter implements RateLimiter {

    protected  static volatile Map<String,Long> map=new ConcurrentHashMap<String, Long>();
    protected  static volatile Map<String,Long> lastPutTimeMap=new ConcurrentHashMap<String, Long>();

    @Override
    public  void counterConsume(String key, long limit) { }


    @Override
    public void tokenConsume(String key, long limit) { }




}
