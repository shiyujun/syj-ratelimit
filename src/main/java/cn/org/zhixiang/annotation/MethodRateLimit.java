package cn.org.zhixiang.annotation;

import java.lang.annotation.*;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/04
 * @描述 限流注解(应用于方法)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodRateLimit {
    /**
     * 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public CheckTypeEnum checkType() default CheckTypeEnum.ALL;
    /**
     * 限流次数。默认值10
     */
    public long limit() default 10;
    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public long refreshInterval() default 60;

    /**--------------------限流算法为令牌桶时的有效配置-----------------------**/
    /**
     * 向令牌桶中添加数据的时间间隔,以秒为单位。默认值1秒
     */
    public long tokenBucketTimeInterval() default 10;
    /**
     * 每次为令牌桶中添加的令牌数量。默认值100个
     */
    public long tokenBucketStepNum() default 5;

}
