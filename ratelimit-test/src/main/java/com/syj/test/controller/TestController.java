package com.syj.test.controller;


import com.syj.annotation.MethodRateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @MethodRateLimit
    @PostMapping("/defult")
    public void defult(@RequestBody Map<String,String> map){
        System.out.println("拦截完毕。。。。");
    }


    @GetMapping("/noParam")
    @MethodRateLimit(limit = 10000000)
    public void noParam(){
        System.out.println("拦截完毕。。。。");
    }

    @GetMapping("/no")
    public void no(){
        System.out.println("拦截完毕。。。。");
    }
}
