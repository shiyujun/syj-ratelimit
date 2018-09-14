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
public class MapRateLimiterCounterImpl extends AbstractMapRateLimiter {



    @Override
    public synchronized void counterConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) {

        long nowTime=System.currentTimeMillis()/1000;
        AtomicLong value=map.get(key);
        if(value!=null){

            long lastClearTime=lastPutTimeMap.get(key);
            if((nowTime-lastClearTime)> lrefreshInterval){
                lastPutTimeMap.put(key,nowTime);
                value.set(0);
            }
            if(value.get()<limit){
                value.incrementAndGet();
                map.put(key,value);
            }else{
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }
        }else{
            map.put(key,new AtomicLong(1));
            lastPutTimeMap.put(key,nowTime);
        }
        log.info("使用计数器算法拦截了key为{}的请求.当前key在{}秒内已进入{}次，此key最大允许进入{}次",key,lrefreshInterval,map.get(key).get(),limit);
    }


}
