package com.syj.util;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述 全局静态常量类
 */
public class Const {

    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public static final long REFRESH_INTERVAL = 60;
    /**
     * 配置rateLimit的前缀
     */
    public static final String PREFIX="syj.rateLimit";

}
