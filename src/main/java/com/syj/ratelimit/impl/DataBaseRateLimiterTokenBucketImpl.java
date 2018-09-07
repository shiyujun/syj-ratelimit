package com.syj.ratelimit.impl;

import com.syj.entity.TokenLimit;
import com.syj.exception.BusinessErrorEnum;
import com.syj.exception.BusinessException;
import com.syj.ratelimit.abs.AbstractDataBaseRateLimiter;
import com.syj.util.Const;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
        log.info("使用令牌桶算法拦截了key为{}的请求.拦截信息存储在数据库中",key);
        Integer value=baseMapper.getKey(key);
        if(value>-1){
            if(value<=0){
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }else{
                baseMapper.updateValue(key,value-1);
            }
        }else{
            baseMapper.insertValueAndLimit(key,limit);

        }
    }

    @Override
    public void tokenLimitIncreaseData() {
        log.info("令牌桶增加数据");
        List<TokenLimit> tokenLimitList=baseMapper.getAll();
        for (TokenLimit tokenLimit:tokenLimitList){
            long maxValue=tokenLimit.getLimit();
            long nowValue=tokenLimit.getValue()+ Const.TOKEN_BUCKET_STEP_NUM;
            if(maxValue>nowValue){
                tokenLimit.setValue(nowValue);
                log.info(""+nowValue);
                log.info(""+tokenLimit.getValue());
            }else {
                tokenLimit.setValue(maxValue);
            }
        }
        baseMapper.batchUpdate(tokenLimitList);
    }
    
}
