package com.syj.annotation;

import com.syj.config.ApplicationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/04
 * @描述 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodRateLimit {
    /**
     * 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public MethodRateLimit.CheckTypeEnum checkType() default MethodRateLimit.CheckTypeEnum.ALL;


    /**
     * 限流次数。默认值1000
     */
    public long limit() default 1000;

    public enum CheckTypeEnum {
        /**
         * 所有请求统一限流。例：此方法1分钟只允许访问n次
         */
        ALL,
        /**
         * 根据IP限流。例：此方法1分钟只允许此IP访问n次
         */
        IP,
        /**
         * 根据用户限流。例：此方法1分钟只允许此用户访问n次
         */
        USER,
        /**
         * 自定义限流方法，详细使用请参考使用文档
         */
        CUSTOM
    }
}
