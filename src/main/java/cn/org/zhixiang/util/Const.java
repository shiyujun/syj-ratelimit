package cn.org.zhixiang.util;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 全局静态常量类
 */
public class Const {
    /**
     * 配置rateLimit的前缀
     */
    public static final String PREFIX="syj-rateLimit";

    /**
     * 自定义拦截方式时的key
     */
    public static final String CUSTOM="syj-rateLimit-custom";

    /**
     * 自定义拦截方式时的key
     */
    public static final String REDIS_ERROR="syj-rateLimit-custom";

    /**--------------------限流算法为默认计数器方法时的配置-----------------------**/
    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public static final long REFRESH_INTERVAL = 60;

    /**--------------------限流算法为令牌桶方法时的配置-----------------------**/
    /**
     * 向令牌桶中添加数据的时间间隔,以秒为单位。默认值1秒
     */
    public static final long TOKEN_BUCKET_TIME_INTERVAL=1;
    /**
     * 每次为令牌桶中添加的令牌数量。默认值100个
     */
    public static final long TOKEN_BUCKET_STEP_NUM=100;


}
