package com.syj.algorithm;

import com.syj.ratelimit.RateLimiter;
import com.syj.util.Const;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 计数器法限流
 */
public class CounterAlgorithm implements RateLimiterAlgorithm {
    @Autowired
    private RateLimiter rateLimiter;

    public CounterAlgorithm(){
        super();
        clear();
    }

    public void consume(String key, long limit){
        rateLimiter.counterConsume(key,limit);
    }

    private  void clear(){
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()-> rateLimiter.counterClear(), 0, Const.REFRESH_INTERVAL, TimeUnit.SECONDS);
    }

}
