package com.syj.config;

import com.syj.annotation.MethodRateLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */
@Aspect
public class AnnotationAspect {

    final static Logger log = LoggerFactory.getLogger(AnnotationAspect.class);

    @Pointcut("@annotation(methodRateLimit)")
    public void annotationPointcut(MethodRateLimit methodRateLimit) {

    }

    @Before("annotationPointcut(methodRateLimit)")
    public void doBefore(JoinPoint joinPoint, MethodRateLimit methodRateLimit) {
        Class<?> target = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName=signature.getMethod().getName();
        log.info("MethodRateLimit  interceptor {},method:{} end.....",target,methodName);
    }


}
