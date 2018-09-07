package com.syj.ratelimit.impl;

import com.syj.ratelimit.abs.AbstractMapRateLimiter;
import com.syj.util.Const;
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
public class MapRateLimiterTokenBucketImpl extends AbstractMapRateLimiter {

    public volatile Map<String,Long> map=new ConcurrentHashMap<String, Long>();
    public volatile Map<String,Long> keyMaxMap=new ConcurrentHashMap<String, Long>();

    @Override
    public void tokenConsume(String key, long limit) {
        if(map.containsKey(key)){
            if(map.get(key)<=0){
                System.out.println("超出限流了，不让进了");
            }else{
                map.replace(key,map.get(key),map.get(key)-1);
            }
        }else{
            map.put(key,limit);
            keyMaxMap.put(key,limit);
        }
    }

    @Override
    public void setTokenLimit() {
        for(Map.Entry<String, Long> entry:map.entrySet()){
            long maxValue=keyMaxMap.get(entry.getKey());
            long nowValue=entry.getValue()+Const.TOKEN_BUCKET_STEP_NUM;
            if(maxValue>nowValue){
                entry.setValue(nowValue);
            }else {
                entry.setValue(maxValue);
            }
        }
    }


}
