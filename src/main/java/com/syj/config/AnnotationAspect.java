package com.syj.config;

import com.syj.algorithm.RateLimiterAlgorithm;
import com.syj.annotation.MethodRateLimit;
import com.syj.util.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 MethodRateLimit注解切面类
 */
@Slf4j
@Aspect
public class AnnotationAspect {

    @Autowired
    private RateLimiterAlgorithm rateLimiterAlgorithm;


    /**
     * 切点
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
        Class<?> target = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //以方法名加参数列表作为唯一标识方法的key
        StringBuffer stringBuffer=new StringBuffer(signature.getMethod().getName());
        Class[] parameterTypes=signature.getParameterTypes();
        for (Class clazz:parameterTypes){
            stringBuffer.append(clazz.getName());
        }
        String methodName=stringBuffer.toString();
        //获取HttpServletRequest对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();
        String key= RateLimiterUtil.getRateKey(methodRateLimit.checkType(),methodName,httpServletRequest);
        rateLimiterAlgorithm.consume(key,methodRateLimit.limit());
        log.info("MethodRateLimit  interceptor {},method:{} end.....",target,methodName);
    }


}
