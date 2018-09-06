package com.syj.ratelimit;




public abstract class RateLimiter {

    public abstract void counterConsume(String key, long limit);

    public abstract void counterClear();

    public abstract void tokenConsume(String key, long limit);

    public abstract void setTokenLimit();



}
