package cn.org.zhixiang.test.controller;

import cn.org.zhixiang.annotation.CheckTypeEnum;
import cn.org.zhixiang.annotation.MethodRateLimit;
import cn.org.zhixiang.test.service.TestService;
import cn.org.zhixiang.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/10
 * Description   展示注解各个功能的例子
 */
@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @Autowired
    private TestService testCstom;

    @MethodRateLimit
    @PostMapping("/defult")
    public void defult(){
        System.out.println("本方法一分钟内只允许进入1000次");
    }

    @MethodRateLimit(checkType = CheckTypeEnum.IP)
    @PostMapping("/ip")
    public void ip(){
        System.out.println("本方法一分钟内只允许同一个IP进入1000次");
    }

    @MethodRateLimit(checkType = CheckTypeEnum.USER)
    @PostMapping("/user")
    public void user(){
        System.out.println("本方法一分钟内只允许同一个用户进入1000次");
    }


    @PostMapping("/custom")
    public void custom(HttpServletRequest httpServletRequest){
        httpServletRequest.setAttribute(Const.CUSTOM,"abcd");
        System.out.println("自定义方法");
        testCstom.testCstom();
    }




}
