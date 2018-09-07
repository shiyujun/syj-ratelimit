package com.syj.ratelimit.impl;

import com.syj.exception.BusinessErrorEnum;
import com.syj.exception.BusinessException;
import com.syj.ratelimit.abs.AbstractMapRateLimiter;
import com.syj.util.RateLimiterUtil;
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
        log.info("使用计数器算法拦截了key为{}的请求.拦截信息存储在Map中",key);
        if(map.containsKey(key)){
            if(map.get(key)<limit){
                map.replace(key,map.get(key),map.get(key)+1);
            }else{
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }
        }else{
            map.put(key,1L);
        }
    }
    @Override
    public void counterClear(){
        log.info("初始化计数器");
        map.clear();
    }

}
