package cn.org.zhixiang.annotation;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/10
 * Description
 */
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

