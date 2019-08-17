# [SpringBoot项目集成cas单点登录](https://www.cnblogs.com/jugglee/p/10564993.html)



## 添加依赖

添加cas client依赖

```xml
        <dependency>
            <groupId>net.unicon.cas</groupId>
            <artifactId>cas-client-autoconfig-support</artifactId>
            <version>2.2.0-GA</version>
        </dependency>
```

## 添加项目配置

```yml
cas:
  server-url-prefix: ${CAS_SERVER:undefined}/cas　　　　　　#cas认证中心地址
  server-login-url: ${CAS_SERVER:undefined}/cas/login　　　#cas认证中心登录地址
  client-host-url: ${clientHostUrl:undefined}　　　　　　　　#后端服务地址
```

## 增加WebMvcConfiguration类
```java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)
         
@Configuration
public class  WebMvcConfiguration  extends WebMvcConfigurerAdapter{

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*").allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE);
    }
}

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)
     
```

## 程序主函数添加注解

```java
@ServletComponentScan
@EnableCasClient
```

 





# 2.简单cas 配置:

1.pom.xml中添加以下依赖:

        <dependency>
            <groupId>net.unicon.cas</groupId>
            <artifactId>cas-client-autoconfig-support</artifactId>
            <version>1.7.0-GA</version>
        </dependency>




2.启动主类中添加以下注解用于开启CAS单点登录:@EnableCasClient

package com.qf.swar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.unicon.cas.client.configuration.EnableCasClient;

@SpringBootApplication
@EnableCasClient
@Controller
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


​	

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello World!";
	}
	
	@RequestMapping("/api")
	@ResponseBody
	public String api() {
		return "Hello api!";
	}


​	
	@RequestMapping(value="/login")
	public String requestMethodName() {
			return "s/index";
	}
}
3.application.properties配置文件中添加以下:

server.port=8083

cas.validation-type=CAS
cas.server-url-prefix=http://localhost:8080/cas
cas.server-login-url=http://localhost:8080/cas/login
cas.client-host-url=http://localhost:8083/login

###指定需要经过CAS验证的链接,未指定的不需要配置

cas.authentication-url-patterns=/login/*,/api/*
4.index.html

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
     <h1>Hello, world!</h1>
     <form action="/hello"  method="post">



​     
     <button type="submit">提价Form</button>
     </form>
     
     <button onclick="cc();">点击</button>
</body>
	<script type="text/javascript">
		function cc(){
			$.ajax({
				url : '/hello',
				type : 'post',
				async: false,//使用同步的方式,true为异步方式
				data : {'id':'1', 'name':'sssss'},//这里使用json对象
				success : function(data){
				//code here...
					alert(data);
				},
				fail:function(){
				//code here...
				}
				});
		}
	
	</script>
</html>
5.运行效果如下:

输入以下地址直接跳转到登陆页面:

http://localhost:8083/login
 ———————————————— 
版权声明：本文为CSDN博主「果壳中de宇宙」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/guokezhongdeyuzhou/article/details/86611994