package com.syj.test.controller;

import com.syj.annotation.CheckTypeEnum;
import com.syj.annotation.MethodRateLimit;
import com.syj.test.service.TestService;
import com.syj.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/10
 * @描述 展示注解各个功能的例子
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
