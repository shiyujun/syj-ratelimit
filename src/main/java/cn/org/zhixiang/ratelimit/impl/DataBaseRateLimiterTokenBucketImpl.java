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
public class DataBaseRateLimiterTokenBucketImpl extends AbstractDataBaseRateLimiter {



    @Override
    public void tokenConsume(String key, long limit) {
        TokenLimit tokenLimit=baseMapper.getKey(key);
        long nowTime=System.currentTimeMillis()/1000;
        if(tokenLimit!=null){
            if((nowTime-tokenLimit.getLastPutTime())> Const.TOKEN_BUCKET_TIME_INTERVAL){
                long maxValue=(nowTime-tokenLimit.getLastPutTime())/Const.TOKEN_BUCKET_TIME_INTERVAL*Const.TOKEN_BUCKET_STEP_NUM;
                if(maxValue>limit){
                    tokenLimit.setValue(limit);
                }else{
                    tokenLimit.setValue(maxValue);
                }
                tokenLimit.setLastPutTime(nowTime);
            }
            if(tokenLimit.getValue()<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                tokenLimit.setValue(tokenLimit.getValue()-1);
            }
        }else{
            tokenLimit=new TokenLimit(key,limit-1,nowTime);
        }
        baseMapper.insert(tokenLimit);
        log.info("使用令牌桶算法拦截了key为{}的请求.当前key在{}秒内已进入{}次，此key最大允许进入{}次",key,Const.TOKEN_BUCKET_TIME_INTERVAL,limit-tokenLimit.getValue(),limit);
    }


    
}
