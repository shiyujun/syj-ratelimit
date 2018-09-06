package com.syj.ratelimit.impl;

import com.syj.dao.BaseMapper;
import com.syj.entity.TokenLimit;
import com.syj.ratelimit.RateLimiter;
import com.syj.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */

@Slf4j
public class DataBaseRateLimiter extends RateLimiter {

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public void counterConsume(String key, long limit) {
        Integer value=baseMapper.getKey(key);
        if(value>-1){
            if(value<limit){
                baseMapper.updateValue(key,value+1);
            }else{
                System.out.println("超出限流了，不让进了");
            }
        }else{
            baseMapper.insert(key,limit);
        }
    }

    @Override
    public void counterClear() {
        baseMapper.clearValue();
    }

    @Override
    public void tokenConsume(String key, long limit) {
        Integer value=baseMapper.getKey(key);
        if(value>-1){
            if(value<=0){
                System.out.println("超出限流了，不让进了");
            }else{
                baseMapper.updateValue(key,value-1);
            }
        }else{
            baseMapper.insertValueAndLimit(key,limit);

        }
    }

    @Override
    public void setTokenLimit() {
        log.info("开始装");
        List<TokenLimit> tokenLimitList=baseMapper.getAll();
        for (TokenLimit tokenLimit:tokenLimitList){
            long maxValue=tokenLimit.getLimit();
            long nowValue=tokenLimit.getValue()+Const.TOKEN_BUCKET_STEP_NUM;
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
