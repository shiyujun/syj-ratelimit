package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractMapRateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.extern.slf4j.Slf4j;

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
    public void tokenConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval) {

        long nowTime=System.currentTimeMillis()/1000;
        if(map.containsKey(key)){
            long lastClearTime=lastPutTimeMap.get(key);
            long nowValue=map.get(key);
            if((nowTime-lastClearTime)> tokenBucketTimeInterval){
                long maxValue=(nowTime-lastClearTime)/tokenBucketTimeInterval*tokenBucketStepNum;
                if(maxValue>limit){
                     nowValue=limit;
                }else{
                    nowValue=maxValue;
                }
                lastPutTimeMap.put(key,nowTime);
            }
            if(nowValue<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                map.put(key,nowValue-1);
            }
        }else{
            lastPutTimeMap.put(key,nowTime);
            map.put(key,limit-1);
        }
        log.info("使用令牌桶算法拦截了key为{}的请求.当前key在{}秒内已进入{}次，此key最大允许进入{}次",key,tokenBucketTimeInterval,limit-map.get(key),limit);
    }




}
