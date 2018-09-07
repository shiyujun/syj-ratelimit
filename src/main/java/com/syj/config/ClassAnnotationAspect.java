package com.syj.config;

import com.syj.algorithm.RateLimiterAlgorithm;
import com.syj.annotation.ClassRateLimit;
import com.syj.annotation.MethodRateLimit;
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



    /**
     * 方法拦截注解切点
     * @param classRateLimit
     */
    @Pointcut("@annotation(classRateLimit)")
    public void annotationPointcut(ClassRateLimit classRateLimit) {

    }

    /**
     * 被@MethodRateLimit注解标识的方法被调用
     * @param joinPoint
     * @param classRateLimit
     */
    @Before("annotationPointcut(classRateLimit)")
    public void doBefore(JoinPoint joinPoint, ClassRateLimit classRateLimit) {
        System.out.println("-----------");
    }


}
