package com.syj.entity;

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
    private long limit;
}
