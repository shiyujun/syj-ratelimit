package com.syj.ratelimit.impl;

import com.syj.ratelimit.abs.AbstractDataBaseRateLimiter;
import lombok.extern.slf4j.Slf4j;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */

@Slf4j
public class DataBaseRateLimiterCounterImpl extends AbstractDataBaseRateLimiter {



    @Override
    public void counterConsume(String key, long limit) {
        Integer value=baseMapper.getKey(key);
        if(value>-1){
            if(value<limit){
                baseMapper.updateValue(key,value+1);
            }else{
                System.out.println("超出限流了，不让进了");
            }
        }else{
            baseMapper.insert(key,limit);
        }
    }

    @Override
    public void counterClear() {
        baseMapper.clearValue();
    }

}
