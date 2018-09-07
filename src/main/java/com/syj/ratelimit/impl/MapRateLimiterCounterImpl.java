package com.syj.ratelimit.impl;

import com.syj.ratelimit.abs.AbstractMapRateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
public class MapRateLimiterCounterImpl extends AbstractMapRateLimiter {

    public volatile Map<String,Long> map=new ConcurrentHashMap<String, Long>();

    @Override
    public void counterConsume(String key, long limit) {
        if(map.containsKey(key)){
            if(map.get(key)<limit){
                map.replace(key,map.get(key),map.get(key)+1);
            }else{
                System.out.println("超出限流了，不让进了");
            }
        }else{
            map.put(key,1L);
        }
    }
    @Override
    public void counterClear(){
        map.clear();
    }

}
