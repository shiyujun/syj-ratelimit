package cn.org.zhixiang.exception;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/07
 * @描述  通用错误信息
 */
public enum BusinessErrorEnum {

    TOO_MANY_REQUESTS("syj-rateLimit say: You have made too many requests,please try again later!!!"),
    USER_NOT_DOUND("syj-rateLimit say: not found user info ,please check request.getUserPrincipal().getName()!!!"),
    CUSTOM_NOT_DOUND("syj-rateLimit say: not found custom info ,please check request.getAttribute('syj-rateLimit-custom')!!!");

    private final String msg;

    BusinessErrorEnum( String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
