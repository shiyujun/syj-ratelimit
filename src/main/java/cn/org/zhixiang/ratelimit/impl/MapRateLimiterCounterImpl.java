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
public class MapRateLimiterCounterImpl extends AbstractMapRateLimiter {



    @Override
    public void counterConsume(String key, long limit) {

        long nowTime=System.currentTimeMillis()/1000;
        if(map.containsKey(key)){
            long value=map.get(key);
            long lastClearTime=lastPutTimeMap.get(key);
            if((nowTime-lastClearTime)> Const.REFRESH_INTERVAL){
                lastPutTimeMap.put(key,nowTime);
                value=0;
            }
            if(value<limit){
                map.put(key,value+1);
            }else{
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }
        }else{
            map.put(key,1L);
            lastPutTimeMap.put(key,nowTime);
        }
        log.info("使用计数器算法拦截了key为{}的请求.当前key在{}秒内已进入{}次，此key最大允许进入{}次",key,Const.REFRESH_INTERVAL,map.get(key),limit);
    }


}
