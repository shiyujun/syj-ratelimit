package cn.org.zhixiang.test;


import cn.org.zhixiang.annotation.EnableSyjRateLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/13
 * Description   加载syj-rateLimit SpringBoot或者SpringCloud环境下可以使用
 */


@EnableSyjRateLimit
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
