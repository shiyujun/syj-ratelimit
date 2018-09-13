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
public class MapRateLimiterCounterImpl extends AbstractMapRateLimiter {

    private static volatile Map<String,Long> map=new ConcurrentHashMap<String, Long>();
    private static volatile long lastClearTime=0;
    @Override
    public void counterConsume(String key, long limit) {
        log.info("使用计数器算法拦截了key为{}的请求.拦截信息存储在Map中",key);
        long nowTime=System.currentTimeMillis()/1000;
        if((nowTime-lastClearTime)> Const.REFRESH_INTERVAL){
            map.clear();
            lastClearTime=nowTime;
        }
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


}
