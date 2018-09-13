package cn.org.zhixiang.ratelimit.abs;

import cn.org.zhixiang.ratelimit.RateLimiter;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
public class AbstractMapRateLimiter implements RateLimiter {

    @Override
    public void counterConsume(String key, long limit) { }


    @Override
    public void tokenConsume(String key, long limit) { }




}
