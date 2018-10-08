package cn.org.zhixiang.annotation;

import java.lang.annotation.*;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/07
 * Description   限流注解(应用于类)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ClassRateLimit {
    /**
     *
     * @return CheckTypeEnum 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public CheckTypeEnum checkType() default CheckTypeEnum.ALL;
    /**
     *
     * @return 限流次数。默认值10
     */
    public long limit() default 10;
    /**
     *
     * @return 限流时间间隔,以秒为单位。默认值60
     */
    public long refreshInterval() default 60;

    /**--------------------限流算法为令牌桶时的有效配置-----------------------**/
    /**
     *
     * @return 向令牌桶中添加数据的时间间隔,以秒为单位。默认值10秒
     */
    public long tokenBucketTimeInterval() default 10;
    /**
     *
     * @return 每次为令牌桶中添加的令牌数量。默认值5个
     */
    public long tokenBucketStepNum() default 5;

}
