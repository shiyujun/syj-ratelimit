package com.syj.test.controller;

import com.syj.EnableSyjRateLimit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@EnableSyjRateLimit
public class TestController {


    @PostMapping("/defult")
    public void defult(@RequestBody Map<String,String> map){
        System.out.println("拦截完毕。。。。");
    }
}
