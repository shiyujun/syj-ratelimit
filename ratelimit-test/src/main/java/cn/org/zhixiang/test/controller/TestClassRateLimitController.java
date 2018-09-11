package cn.org.zhixiang.test.controller;


import cn.org.zhixiang.annotation.ClassRateLimit;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/10
 * @描述 将注解应用于类的例子
 */
@ClassRateLimit
@RestController
@RequestMapping("/testClass")
public class TestClassRateLimitController {


    @PostMapping("/havaParam")
    public void havaParam(@RequestBody Map<String,String> map){
        System.out.println("拦截完毕。。。。");
    }


    @GetMapping("/noParam")
    public void noParam(){
        System.out.println("拦截完毕。。。。");
    }


}
