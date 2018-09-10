package com.syj.config;

import com.syj.algorithm.RateLimiterAlgorithm;
import com.syj.annotation.ClassRateLimit;
import com.syj.util.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 MethodRateLimit注解切面类
 */
@Slf4j
@Aspect
public class ClassAnnotationAspect {

    @Autowired
    private RateLimiterAlgorithm rateLimiterAlgorithm;




    @Pointcut("@within(classRateLimit)")
    public void annotationPointcut(ClassRateLimit classRateLimit) {
    }



    @Before("@within(classRateLimit)")
    public void doBefore(JoinPoint joinPoint, ClassRateLimit classRateLimit) {
        String key= RateLimiterUtil.getRateKey(joinPoint,classRateLimit.checkType());
        rateLimiterAlgorithm.consume(key,classRateLimit.limit());
    }



}
