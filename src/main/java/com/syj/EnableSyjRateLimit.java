package com.syj;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/04
 * @描述 限流注解
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(SyjLimitAutoConfiguration.class)
@Documented
@Inherited
public @interface EnableSyjRateLimit {
    /**
     * 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public String checkType() default "ALL";

    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public String refreshInterval() default "60";
}
