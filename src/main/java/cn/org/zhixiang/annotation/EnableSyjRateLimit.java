package cn.org.zhixiang.annotation;


import cn.org.zhixiang.config.ApplicationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/04
 * @描述 开启限流功能的标识注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ApplicationConfiguration.class)
@Documented
@Inherited
public @interface EnableSyjRateLimit {

}
