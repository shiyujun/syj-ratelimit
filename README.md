# syj-ratelimit
***

## 项目介绍
>此项目为一个无侵入的网关限流项目,如果您正在寻找一个网关限流的插件，使用syj-ratelimit是最明智的选择<br>
### 为什么选择syj-ratelimit
1. 无任何代码侵入，只需2行配置甚至是不需要任何配置<br>
2.  细粒度控制，你可以控制同一个类中的A方法每分钟限流100而B方法每分钟限流200<br>
3.  高灵活性，可根据自定义信息（如用户id、用户ip、用户权限等）进行限流、可灵活选择限流算法、可灵活选择存储介质<br>
4.  高可用性，支持数据库或redis为分布式系统保驾护航<br>
5.  高可扩展性，限流算法和存储介质可自由添加且不改变原有代码
## Quick Start
### 1.  引入syj-ratelimit
```xml
<dependency>
    <groupId>cn.org.zhixiang</groupId>
    <artifactId>syj-ratelimit</artifactId>
    <version>0.0.1</version>
 </dependency>
 ```
### 2.  注册syj-ratelimit
>因为并不是所有的项目都会使用SpringBoot,所以在注册这一步我们分为两种情况
#### 1.SpringBoot
您需要在启动类上增加一个注解
```java
@EnableSyjRateLimit
```
#### 2.Spring
您需要提供一个可以被Spring管理的配置类。比如说：
```java
@Import(EnableSyjRateLimitConfiguration.class)
@Configuration
public class SyjRateLimitConfig {
}
```
### 3.  使用syj-ratelimit
>其实看到这一步的时候您已经可以使用syj-ratelimit来进行限流了哦。下面给出几个使用的例子
1. 一个对外提供的接口类所有方法都要限流。（例如，需要每个方法每分钟只允许调用10次）<br>
```java
@ClassRateLimit(limit = 10)
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
```
2.  我感觉1太不公平了，可能2个人访问havaParam，1个人先访问9次第二个人只能访问一次
```java
@ClassRateLimit(limit = 10,checkType = CheckTypeEnum.IP)//每个IP每分钟可以访问10次
```
3. 我感觉2也不大好，有人伪造IP怎么办
```java
@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @Autowired
    private TestService testCstom;

    @PostMapping("/custom")
    public void custom(HttpServletRequest httpServletRequest){
        //根据一系列操作查出来了用户id
        httpServletRequest.setAttribute(Const.CUSTOM,"用户id");
        testCstom.testCstom();
    }
}

@ClassRateLimit(limit = 10,checkType = CheckTypeEnum.CUSTOM)
public class TestService {
    public  void testCstom(){
        System.out.println("本方法一分钟内只允许符合用户自定义的属性进入1000次");
    }
}
```
4.  我感觉前3个都不好，同一个类中我就只想拦截一个方法
```java
@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @PostMapping("/defult")
    public void defult(){
        System.out.println("没有拦截");
    }

    @PostMapping("/ip")
    public void ip(){
        System.out.println("没有拦截");
    }

    @MethodRateLimit(checkType = CheckTypeEnum.USER)
    @PostMapping("/user")
    public void user(){
        System.out.println("根据用户信息拦截");
    }

}
```
注：Quick Start只针对单机版系统或者本地测试使用，如果您的系统是分布式系统请参考下方的分布式部署指南<br>
