package cn.org.zhixiang.entity;

import lombok.Data;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/06
 * @描述
 */
@Data
public class TokenLimit {
    private String key;
    private long value;
    private long lastPutTime;

    public TokenLimit(String key,long value,long lastPutTime){
        this.key=key;
        this.value=value;
        this.lastPutTime=lastPutTime;
    }
}
