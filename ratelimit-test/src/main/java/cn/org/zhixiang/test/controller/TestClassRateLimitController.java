package cn.org.zhixiang.test.controller;


import cn.org.zhixiang.annotation.CheckTypeEnum;
import cn.org.zhixiang.annotation.ClassRateLimit;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/10
 * Description   将注解应用于类的例子
 */
@ClassRateLimit(limit = 10,checkType = CheckTypeEnum.CUSTOM)
@RestController
@RequestMapping("/testClass")
public class TestClassRateLimitController {


    @PostMapping("/havaParam")
    public void havaParam(@RequestBody Map<String,String> map){
        System.out.println("业务逻辑。。。。");
    }


    @GetMapping("/noParam")
    public void noParam(){
        System.out.println("业务逻辑。。。。");
    }


}
