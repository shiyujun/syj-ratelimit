package com.syj.util;

import com.syj.annotation.MethodRateLimit;

import javax.servlet.http.HttpServletRequest;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 RateLimiter工具类
 */
public class RateLimiterUtil {



    public static String getRateKey(MethodRateLimit.CheckTypeEnum checkTypeEnum, String methodName, HttpServletRequest request){
        String key=null;
        if(MethodRateLimit.CheckTypeEnum.ALL.equals(checkTypeEnum)){
            key=methodName;
        }else if(MethodRateLimit.CheckTypeEnum.USER.equals(checkTypeEnum)){
            key= request.getUserPrincipal().getName();
        }else if(MethodRateLimit.CheckTypeEnum.IP.equals(checkTypeEnum)){
            key= request.getRemoteAddr();
        }else{
            key=request.getAttribute("SyjRateLimiterCustom").toString();
        }
        return key;
    }
}
