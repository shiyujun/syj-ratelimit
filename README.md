# syj-ratelimit
***

## 项目介绍
>此项目为一个无侵入的网关限流插件,如果您正在寻找一个网关限流的插件，使用syj-ratelimit是最明智的选择<br>
### 为什么选择syj-ratelimit
1. 无任何代码侵入，最多需要1行配置来选择实现的算法<br>
2.  细粒度控制，你可以控制同一个类中的A方法每分钟限流100而B方法每分钟限流200<br>
3.  高灵活性，可根据自定义信息（如用户id、用户ip、用户权限等）进行限流、可灵活选择限流算法<br>
4.  高可用性，使用redis+lua脚本的原子性为分布式系统保驾护航<br>
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
#### 1.SpringBoot或SpringCloud项目
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
### 3. 配置你的redis连接
>您需要配置您的redis连接为syj-ratelimit，同2的情况我们把项目分为两种情况（注意下方的配置需要根据实际情况调整）
#### 1.SpringBoot或SpringCloud项目
```yaml
spring:
  redis:
    host: 
    port: 
    password:
    pool:
      max-active: 8
      max-wait: 1
      max-idle: 8
      min-idle: 0
    timeout: 2000
```
#### 2. Spring应用
您只需要注册一个RedisConnectionFactory子类的bean。比如说
```xml
<beans>
    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig" >
        <property name="maxIdle" value="${redis.maxIdle}" />
        <property name="maxWaitMillis" value="${redis.maxWait}" />
        <property name="testOnBorrow" value="${redis.testOnBorrow}" />
    </bean>
    <bean id="connectionFactory"  class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory" >
        <property name="poolConfig" ref="poolConfig" />
        <property name="port" value="${redis.port}" />
        <property name="hostName" value="${redis.host}" />
        <property name="timeout" value="${redis.timeout}" ></property>
        <property name="database" value="1"></property>
    </bean>
</beans>
```
### 4.  使用syj-ratelimit
>其实看到这一步的时候您已经可以使用syj-ratelimit来进行限流了哦。<br>

##### syj-ratelimit为您提供了两个注解来进行限流，它们是@ClassRateLimit和@MethodRateLimit。顾名思义，它们一个是用在类上的一个是用在方法上的。他们的功能是一样的，之所以分出来两个注解的原因就是为了解决当一个类的不同接口需要进行不同的限流方案问题

因为两个注解的内容一样，所以我们先以@ClassRateLimit为例看一下其中的几个属性

```java
public @interface ClassRateLimit {
    /**
     * 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public CheckTypeEnum checkType() default CheckTypeEnum.ALL;
    /**
     * 限流次数。默认值10
     */
    public long limit() default 10;
    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public long refreshInterval() default 60;

}
```
来几个使用的例子吧<br>
1. 一个对外提供的接口类所有方法都要限流。（例如，需要每个方法每30秒只允许调用10次）<br>
```java
@ClassRateLimit(limit = 10,refreshInterval=30)
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
@ClassRateLimit(limit = 10,refreshInterval=30,checkType = CheckTypeEnum.IP)//每个IP每30秒可以访问10次
```
3. 我感觉2也不大好，有人伪造IP怎么办（自定义时推荐在controller中查出能标识用户唯一性的值放入request中，然后再service中进行限流）
```java
@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @Autowired
    private TestService testCstom;

    @PostMapping("/custom")
    public void custom(HttpServletRequest httpServletRequest){
        //根据一系列操作查出来了用户id
        httpServletRequest.setAttribute(Const.CUSTOM,"用户id");//限流时在httpServletRequest中根据Const.CUSTOM的值进行限流
        testCstom.testCstom();
    }
}

@ClassRateLimit(limit = 10,refreshInterval=30,checkType = CheckTypeEnum.CUSTOM)
public class TestService {
    public  void testCstom(){
        System.out.println("此方法每个key为Const.CUSTOM的用户每30秒可以进入10次");
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
        System.out.println("根据用户信息拦截");//用户信息取自request.getUserPrincipal()
    }

}
```
5. 既然使用了@MethodRateLimit，那么如何证明它的灵活性呢
```java
@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @PostMapping("/defult")
    public void defult(){
        System.out.println("没有拦截");
    }

    @PostMapping("/ip")
    @MethodRateLimit(limit = 30,refreshInterval=120,checkType = CheckTypeEnum.IP)
    public void ip(){
        System.out.println("根据用户IP拦截，单个IP2分钟可以进入次方法30次");
    }

    @MethodRateLimit(limit = 300,refreshInterval=10,checkType = CheckTypeEnum.USER)
    @PostMapping("/user")
    public void user(){
        System.out.println("单个用户10秒中可以进入300次");
    }
}
```

## 更多信息

>相信看完了上方的Quick Start你已经迫不及待的想要将syj-ratelimit应用于生产了。我在这里为您提供了两种限流算法

#### 限流算法
> 如果您对限流算法不太了解的话可以先参考一下这篇文章[http://zhixiang.org.cn](http://zhixiang.org.cn)
1.  计数器法<br>
程序默认使用计数器算法进行限流，如果您要使用计数器法的话无需要额外的配置。
2.  令牌桶算法<br>
如果您想要使用令牌桶算法的话，那么有两个需要注意的地方。
    1. 再配置文件中指定算法为令牌桶算法。（推荐您使用yml文件或者properties文件）
        1. yml
        ```yaml
           syj-rateLimit:
             algorithm: token
       ```
        2. properties
        ```properties
               syj-rateLimit.algorithm= token
       ```
    2.  您需要将目光放到@ClassRateLimit上的另外两个属性上
    ```java
        /**
         * 向令牌桶中添加数据的时间间隔,以秒为单位。默认值1秒
         */
        public long tokenBucketTimeInterval() default 10;
        /**
         * 每次为令牌桶中添加的令牌数量。默认值100个
         */
        public long tokenBucketStepNum() default 5;
    ```
#### 再次开发
> 如果您觉得您有相比这两种算法更加强大的算法，您可以[在这](https://github.com/2388386839/syj-ratelimit)fork项目进行开发

为了遵守代码的开闭原则，您在添加新的限流算法时请参考包ratelimit、config和algorithm 

#### 作者信息
1. [GitHub](https://github.com/2388386839)
2. [码云](https://gitee.com/zhixiang_blog)
3. [个人网站](http://zhixiang.org.cn)
#### 项目实现设计的技术
1.  [如何使用Redis执行Lua脚本](http://zhixiang.org.cn)
2.  [我是如何把自定义注解应用到生产的](http://zhixiang.org.cn)
3.  [大型网站限流算法的实现和改造](http://zhixiang.org.cn)
4.  [IDEA中使用lombok插件](http://zhixiang.org.cn/2018/08/16/IDEA%E4%B8%AD%E4%BD%BF%E7%94%A8lombok%E6%8F%92%E4%BB%B6/)，
[lombok高级功能之@Conditional注解](http://zhixiang.org.cn)
5.  [策略模式](http://zhixiang.org.cn/2018/07/26/%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F/)
6.  [如何将自己的jar包发布到mavan中央仓库](http://zhixiang.org.cn)


