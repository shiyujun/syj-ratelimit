package com.syj.ratelimit.abs;

import com.syj.dao.BaseMapper;
import com.syj.ratelimit.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */


public class AbstractDataBaseRateLimiter implements RateLimiter {

    @Autowired
    protected BaseMapper baseMapper;

    @Override
    public void counterConsume(String key, long limit) { }

    @Override
    public void counterClear() { }

    @Override
    public void tokenConsume(String key, long limit) { }

    @Override
    public void tokenLimitIncreaseData() { }
}
