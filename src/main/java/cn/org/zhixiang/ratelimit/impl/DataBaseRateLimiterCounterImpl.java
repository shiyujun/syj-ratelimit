package cn.org.zhixiang.ratelimit.impl;

import cn.org.zhixiang.exception.BusinessErrorEnum;
import cn.org.zhixiang.exception.BusinessException;
import cn.org.zhixiang.ratelimit.abs.AbstractDataBaseRateLimiter;
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
        log.info("使用计数器算法拦截了key为{}的请求.拦截信息存储在数据库中",key);
        Integer value=baseMapper.getKey(key);
        if(value>-1){
            if(value<limit){
                baseMapper.updateValue(key,value+1);
            }else{
                throw new BusinessException(BusinessErrorEnum.TOO_MANY_REQUESTS);
            }
        }else{
            baseMapper.insert(key,limit);
        }
    }

    @Override
    public void counterClear() {
        log.info("初始化计数器");
        baseMapper.clearValue();
    }

}
