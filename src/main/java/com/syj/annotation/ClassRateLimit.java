package com.syj.annotation;

import java.lang.annotation.*;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/07
 * @描述
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ClassRateLimit {
}
