package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractMapRateLimiter;
import cn.org.zhixiang.util.Const;
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

    private volatile Map<String,Long> map=new ConcurrentHashMap<String, Long>();
    private volatile Map<String,Long> lastPutTimeMap=new ConcurrentHashMap<String, Long>();


    @Override
    public void tokenConsume(String key, long limit) {
        log.info("使用令牌桶算法拦截了key为{}的请求.拦截信息存储在Map中",key);
        long nowTime=System.currentTimeMillis()/1000;
        if(map.containsKey(key)){
            long lastClearTime=lastPutTimeMap.get(key);
            long nowValue=map.get(key);
            if((nowTime-lastClearTime)> Const.TOKEN_BUCKET_TIME_INTERVAL){
                if(nowValue>=limit){
                    long maxValue=(nowTime-lastClearTime)/Const.TOKEN_BUCKET_TIME_INTERVAL*Const.TOKEN_BUCKET_STEP_NUM;
                    if(maxValue>limit){
                        nowValue=limit;
                    }else{
                        nowValue=maxValue;
                    }
                    lastPutTimeMap.put(key,nowTime);
                }
            }
            if(nowValue<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                map.replace(key,nowValue,nowValue-1);
            }
        }else{
            lastPutTimeMap.put(key,nowTime);
            map.put(key,limit-1);
        }

    }




}
