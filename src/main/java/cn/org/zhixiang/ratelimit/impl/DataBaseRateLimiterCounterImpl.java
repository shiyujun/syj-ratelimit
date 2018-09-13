package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.entity.TokenLimit;
import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractDataBaseRateLimiter;
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
public class DataBaseRateLimiterCounterImpl extends AbstractDataBaseRateLimiter {

    private static volatile long lastClearTime=0;

    @Override
    public void counterConsume(String key, long limit) {
        log.info("使用计数器算法拦截了key为{}的请求.拦截信息存储在数据库中",key);
        TokenLimit tokenLimit=baseMapper.getKey(key);
        long nowTime=System.currentTimeMillis()/1000;
        if(tokenLimit!=null){
            if((nowTime-tokenLimit.getLastPutTime())> Const.REFRESH_INTERVAL){
                tokenLimit.setValue(0);
                tokenLimit.setLastPutTime(nowTime);
            }
            if(tokenLimit.getValue()<limit){
                tokenLimit.setValue(tokenLimit.getValue()+1);
            }else{
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }
        }else{
            new TokenLimit(key,1,nowTime);
        }
        baseMapper.insert(tokenLimit);

    }



}
