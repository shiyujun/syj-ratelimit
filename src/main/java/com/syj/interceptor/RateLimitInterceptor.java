/*

package com.syj.interceptor;


import com.syj.annotation.EnableSyjRateLimit;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;

*/
/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/04
 * @描述 核心拦截器
 *//*



@RestControllerAdvice
public class RateLimitInterceptor implements RequestBodyAdvice  {

    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        //https://blog.csdn.net/qq_16504067/article/details/73225005
        //HttpEntityMethodProcessor
        System.out.println("supports.....");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        EnableSyjRateLimit enableSyjRateLimit= methodParameter.getMethodAnnotation(EnableSyjRateLimit.class);
        System.out.println(enableSyjRateLimit.refreshInterval());


        return false;
    }

    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("handleEmptyBody.....");
        return null;
    }

    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        System.out.println("beforeBodyRead.....");
        return null;
    }

    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("afterBodyRead.....");
        return null;
    }



}


*/
