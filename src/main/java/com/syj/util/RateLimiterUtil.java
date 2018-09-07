package com.syj.util;

import com.syj.annotation.MethodRateLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 RateLimiter工具类
 */
public class RateLimiterUtil {
    /**
     * 获取唯一标识此次请求的key
     * @param joinPoint
     * @param checkTypeEnum
     * @return
     */
    public static String getRateKey(JoinPoint joinPoint, MethodRateLimit.CheckTypeEnum checkTypeEnum){
        //以方法名加参数列表作为唯一标识方法的key
        if(MethodRateLimit.CheckTypeEnum.ALL.equals(checkTypeEnum)){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            StringBuffer stringBuffer=new StringBuffer(signature.getMethod().getName());
            Class[] parameterTypes=signature.getParameterTypes();
            for (Class clazz:parameterTypes){
                stringBuffer.append(clazz.getName());
            }
            String methodName=stringBuffer.toString();
            return methodName;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //以用户信息作为key
        if(MethodRateLimit.CheckTypeEnum.USER.equals(checkTypeEnum)){
            return request.getUserPrincipal().getName();
        }
        //以IP地址作为key
        if(MethodRateLimit.CheckTypeEnum.IP.equals(checkTypeEnum)){
            return request.getRemoteAddr();
        }
        //以自定义内容作为key
        if(MethodRateLimit.CheckTypeEnum.CUSTOM.equals(checkTypeEnum)){
            return request.getAttribute(Const.CUSTOM).toString();
        }
        return null;
    }

}
