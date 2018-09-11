package cn.org.zhixiang.algorithm;

import cn.org.zhixiang.ratelimit.RateLimiter;
import cn.org.zhixiang.util.Const;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 计数器法限流
 */
@Service
@DependsOn("rateLimiter")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = Const.PREFIX, name = "algorithm", havingValue = "counter", matchIfMissing = true)
public class CounterAlgorithm implements RateLimiterAlgorithm {
    @NonNull
    private RateLimiter rateLimiter;


    public void consume(String key, long limit){
        rateLimiter.counterConsume(key,limit);
    }

    @PostConstruct
    private  void init(){
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()-> rateLimiter.counterClear(), 0, Const.REFRESH_INTERVAL, TimeUnit.SECONDS);
    }

}
