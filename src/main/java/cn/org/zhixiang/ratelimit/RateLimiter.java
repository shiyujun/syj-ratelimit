package cn.org.zhixiang.ratelimit;




public interface  RateLimiter {

    public  void counterConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval);


    public  void tokenConsume(String key, long limit, long lrefreshInterval, long tokenBucketStepNum, long tokenBucketTimeInterval);





}
