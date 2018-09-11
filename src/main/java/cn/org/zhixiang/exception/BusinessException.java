package cn.org.zhixiang.exception;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/07
 * @描述 业务异常信息类
 */
public class BusinessException extends RuntimeException {
    private String msg;
    private Integer status;

    public BusinessException(BusinessErrorEnum error){
        this.msg = error.getMsg();
    }

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

