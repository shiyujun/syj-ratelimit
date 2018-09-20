package cn.org.zhixiang.aspect;

import cn.org.zhixiang.algorithm.RateLimiterAlgorithm;
import cn.org.zhixiang.annotation.MethodRateLimit;
import cn.org.zhixiang.util.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/05
 * Description   MethodRateLimit注解切面类
 */
@Slf4j
@Aspect
@Component
public class MethodAnnotationAspect {

    @Autowired
    private RateLimiterAlgorithm rateLimiterAlgorithm;


    /**
     * 方法拦截注解切点
     * @param methodRateLimit 注解
     */
    @Pointcut("@annotation(methodRateLimit)")
    public void annotationPointcut(MethodRateLimit methodRateLimit) {

    }

    /**
     * 被@MethodRateLimit注解标识的方法被调用
     * @param joinPoint 切点
     * @param methodRateLimit 注解
     */
    @Before("annotationPointcut(methodRateLimit)")
    public void doBefore(JoinPoint joinPoint, MethodRateLimit methodRateLimit) {
        String key= RateLimiterUtil.getRateKey(joinPoint,methodRateLimit.checkType());
        rateLimiterAlgorithm.consume(key,methodRateLimit.limit(),methodRateLimit.refreshInterval(),methodRateLimit.tokenBucketStepNum(),methodRateLimit.tokenBucketTimeInterval());
    }



}
