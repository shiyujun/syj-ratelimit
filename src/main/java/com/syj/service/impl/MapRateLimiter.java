package com.syj.service.impl;

import com.syj.annotation.MethodRateLimit;
import com.syj.service.RateLimiter;
import com.syj.util.Const;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
public class MapRateLimiter extends RateLimiter {
    public volatile Map<String,Long> map=new ConcurrentHashMap<String, Long>();
    public volatile Map<String,Long> keyMaxMap=new ConcurrentHashMap<String, Long>();
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
            System.out.println(entry.getKey()+"/"+entry.getValue());
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
