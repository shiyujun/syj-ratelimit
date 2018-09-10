package com.syj.config;

import com.syj.algorithm.RateLimiterAlgorithm;
import com.syj.annotation.MethodRateLimit;
import com.syj.util.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 MethodRateLimit注解切面类
 */
@Slf4j
@Aspect
@Component
public class MethodAnnotationAspect {

    @Autowired
    private RateLimiterAlgorithm rateLimiterAlgorithm;


    /**
     * 方法拦截注解切点
     * @param methodRateLimit
     */
    @Pointcut("@annotation(methodRateLimit)")
    public void annotationPointcut(MethodRateLimit methodRateLimit) {

    }

    /**
     * 被@MethodRateLimit注解标识的方法被调用
     * @param joinPoint
     * @param methodRateLimit
     */
    @Before("annotationPointcut(methodRateLimit)")
    public void doBefore(JoinPoint joinPoint, MethodRateLimit methodRateLimit) {
        String key= RateLimiterUtil.getRateKey(joinPoint,methodRateLimit.checkType());
        rateLimiterAlgorithm.consume(key,methodRateLimit.limit());
    }



}
