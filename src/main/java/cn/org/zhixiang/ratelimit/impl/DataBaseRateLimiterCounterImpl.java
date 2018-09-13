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



    @Override
    public void counterConsume(String key, long limit) {

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
            tokenLimit=new TokenLimit(key,1,nowTime);
        }
        baseMapper.insert(tokenLimit);
        log.info("使用计数器算法拦截了key为{}的请求.当前key在{}秒内已进入{}次，此key最大允许进入{}次",key,Const.REFRESH_INTERVAL,tokenLimit.getValue(),limit);
    }



}
