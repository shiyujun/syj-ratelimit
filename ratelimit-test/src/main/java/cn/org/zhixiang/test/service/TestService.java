package cn.org.zhixiang.test.service;

import cn.org.zhixiang.annotation.CheckTypeEnum;
import cn.org.zhixiang.annotation.MethodRateLimit;
import org.springframework.stereotype.Service;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/10
 * @描述
 */
@Service
public class TestService {

    @MethodRateLimit(checkType = CheckTypeEnum.CUSTOM)
    public  void testCstom(){
        System.out.println("本方法一分钟内只允许符合用户自定义的属性进入1000次");
    }
}
