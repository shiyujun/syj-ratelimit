package com.syj.entity;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/06
 * @描述
 */
public class TokenLimit {
    private String key;
    private long value;
    private long limit;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }
}
