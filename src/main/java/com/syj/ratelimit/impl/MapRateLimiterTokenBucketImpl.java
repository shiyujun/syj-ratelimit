package com.syj.ratelimit.impl;

import com.syj.exception.BusinessErrorEnum;
import com.syj.exception.BusinessException;
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
        log.info("使用令牌桶算法拦截了key为{}的请求.拦截信息存储在Map中",key);
        if(map.containsKey(key)){
            if(map.get(key)<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                map.replace(key,map.get(key),map.get(key)-1);
            }
        }else{
            map.put(key,limit);
            keyMaxMap.put(key,limit);
        }
    }

    @Override
    public void tokenLimitIncreaseData() {
        log.info("令牌桶增加数据");
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
