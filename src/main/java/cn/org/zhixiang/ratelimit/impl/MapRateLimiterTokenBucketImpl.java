package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractMapRateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Slf4j
public class MapRateLimiterTokenBucketImpl extends AbstractMapRateLimiter {




    @Override
    public synchronized void tokenConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) {

        long nowTime=System.currentTimeMillis()/1000;
        AtomicLong nowValue=map.get(key);
        if(nowValue!=null){
            long lastClearTime=lastPutTimeMap.get(key);
            if((nowTime-lastClearTime)> tokenBucketTimeInterval){
                long maxValue=nowValue.get()+(nowTime-lastClearTime)/tokenBucketTimeInterval*tokenBucketStepNum;
                if(maxValue>limit){
                    nowValue.set(limit);
                }else{
                    nowValue.set(maxValue);
                }
                lastPutTimeMap.put(key,nowTime);
            }
            if(nowValue.get()<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                nowValue.decrementAndGet();
                map.put(key,nowValue);
            }
        }else{
            lastPutTimeMap.put(key,nowTime);
            map.put(key,new AtomicLong(limit-1));
        }
        log.info("使用令牌桶算法拦截了key为{}的请求.当前key在{}秒内已进入{}次，此key最大允许进入{}次",key,tokenBucketTimeInterval,limit-map.get(key).get(),limit);
    }




}
