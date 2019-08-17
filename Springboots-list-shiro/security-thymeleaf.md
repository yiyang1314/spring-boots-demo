# 《SpringBoot集成Security权限管理》

[![96](https://upload.jianshu.io/users/upload_avatars/11009501/3d5945d3-8752-422a-a8d2-193f36e6b061.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96)](https://www.jianshu.com/u/460839314f76) 

### 简介

SpringBoot集成Security框架，实现用户角色功能权限管理，很简单的，确实这要得益于SpringBoot简化配置的优点。

这节我们要实现通过数据库表管理用户角色权限，dao层使用MyBatis，如果对MyBatis比较陌生，建议先去了解一下，或者去看我的这篇文章[《SpringBoot集成MyBatis》](https://www.jianshu.com/p/c558bc213d0c)

### 准备数据表

我们还是采用经典的角色权限5张表：
１、用户表（sys_user）：id、username、password
２、角色表（sys_role）：id、name
３、权限表（sys_permission）：id、name、descript、url、pid
４、用户角色表（sys_user_role）：id、uid、rid
５、角色权限表（sys_role_permission）：id、rid、pid

插入数据：
**sys_user表**



![img](https://upload-images.jianshu.io/upload_images/11009501-3734f4f76f37c649.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/221/format/webp)

image.png)


**sys_role表**



![img](https://upload-images.jianshu.io/upload_images/11009501-a156b8d1860cb985.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/161/format/webp)

image.png）


**sys_permission表**



![img](https://upload-images.jianshu.io/upload_images/11009501-51246d2ea9269c26.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/426/format/webp)

image.png）


**sys_user_role表**



![img](https://upload-images.jianshu.io/upload_images/11009501-9ec42239fc71d334.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/199/format/webp)

image.png


**sys_role_permission表**



![img](https://upload-images.jianshu.io/upload_images/11009501-484bde32109998d8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/218/format/webp)

image.png



### 集成Security

- **1.引入maven依赖**
  我们会用到视图层解析，这里用Thymeleaf去解析。

```
        <!--Security权限管理-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity4</artifactId>
        </dependency>
        <!--thymeleaf-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```

- **2.配置.properties文件**
  你也可以将 security.basic.enabled=true 打开，会有基础登录弹窗弹出。这里我们暂时不需要，所以将它关闭，之后我们自己实现登录界面。

```
#Security
security.basic.enabled=false
security.user.name=admin
security.user.password=123456

#thymeleaf start
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.check-template-location=true
spring.thymeleaf.template-resolver-order=1
spring.thymeleaf.suffix=.html
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.content-type=text/html
spring.thymeleaf.mode=HTML5
```

- **3.创建权限管理相关类**

------

以下创建的几个类包括：

**CustomUserServiceImpl类**
实现了UserDetailsService接口，这个接口很简单就一个方法
UserDetails loadUserByUsername(String var1)
会返回一个用户基本信息和该用户拥有的权限功能 （by username），作为后续权限认证的依据，也是和Dao层通信的主要接口。

**MyInvocationSecurityMetadataSourceService类**
实现了FilterInvocationSecurityMetadataSource接口，类似Holder主要实现加载缓存权限功能路径和名称，以及提供从请求路径查找权限名称，供后续权限决策管理器去判定使用。

**MyAccessDecisionManager类**
实现了AccessDecisionManager接口，主要判定用户是否拥有权限的决策方法，有权限放行，无权限拒绝访问。

**MyFilterSecurityInterceptor类**
继承AbstractSecurityInterceptor抽象类，权限管理Security真正的拦截器，并绑定了MyAccessDecisionManager（处理权限认证）和MyInvocationSecurityMetadataSourceService（提供请求路径和权限名称元数据）

**WebSecurityConfig类**
继承WebSecurityConfigurerAdapter抽象类，主要是最后配置Security，配置之前定义的拦截器、提供用户基本权限信息、以及一些访问控制。

------

代码示例：
**CustomUserServiceImpl**

```
import com.yu.scloud.baseframe.frame.dao.SysPermissionMapper;
import com.yu.scloud.baseframe.frame.dao.SysUserMapper;
import com.yu.scloud.baseframe.frame.model.SysPermission;
import com.yu.scloud.baseframe.frame.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserServiceImpl implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SysUserMapper userDao;
    @Autowired
    SysPermissionMapper permissionDao;

    //返回 user和user拥有的权限功能 by username
    public UserDetails loadUserByUsername(String username) {
        SysUser user = userDao.findByUserName(username);
        if (user != null) {
            List<SysPermission> permissions = permissionDao.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (SysPermission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}
```

**MyInvocationSecurityMetadataSourceService**

```
import com.yu.scloud.baseframe.frame.dao.SysPermissionMapper;
import com.yu.scloud.baseframe.frame.model.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
public class MyInvocationSecurityMetadataSourceService  implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPermissionMapper permissionDao;

    //缓存
    private HashMap<String, Collection<ConfigAttribute>> map =null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysPermission> permissions = permissionDao.findAll();
        for(SysPermission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }

    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map ==null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
```

**MyAccessDecisionManager**

```
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;


@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    // decide 方法是判定是否拥有权限的决策方法，
    //authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
    //object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
    //configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if(null== configAttributes || configAttributes.size() <=0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for(Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            needRole = c.getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()) {//authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
                if(needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
```

**MyFilterSecurityInterceptor**

```
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {


    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
```

**WebSecurityConfig**

```
import com.yu.scloud.baseframe.frame.service.impl.CustomUserServiceImpl;
import com.yu.scloud.baseframe.frame.service.impl.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;



    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()); //user Details Service验证

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(new String[]{"/plugins/**"}).permitAll()//
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll()
                .and()
                .headers()
                .frameOptions().sameOrigin(); ; //注销行为任意访问
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题（plugins目录在工程resources/static/下）
        web.ignoring().antMatchers("/plugins/**","/login.html");
    }
}
```

- **4.创建MyBatis方式，相关实体类、Dao、Service以及Controller**
  实体类以及部分Dao我就不贴出来了，查看最前面的数据库表字段就可以。或者使用MyBatis自动代码生成器生成，快捷方便，如果还没掌握它怎么使用，请看我的一篇文章[《MyBatis代码自动生成器》](https://www.jianshu.com/p/2ad2dda63756)

------

**SysUserMapper.xml**

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yu.scloud.baseframe.frame.dao.SysUserMapper">

  <select id="findByUserName" parameterType="String" resultType="com.yu.scloud.baseframe.frame.model.SysUser">
    SELECT * FROM sys_user WHERE username=#{username};
  </select>

</mapper>
```

**SysPermissionMapper.xml**

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yu.scloud.baseframe.frame.dao.SysPermissionMapper">

  <select id="findAll"  resultType="com.yu.scloud.baseframe.frame.model.SysPermission">
    SELECT * from sys_permission;
  </select>

  <select id="findByAdminUserId" parameterType="int" resultType="com.yu.scloud.baseframe.frame.model.SysPermission">
    select p.*
    from sys_user u
    LEFT JOIN sys_user_role sur on u.id= sur.uid
    LEFT JOIN sys_role r on sur.rid=r.id
    LEFT JOIN sys_role_permission srp on srp.rid=r.id
    LEFT JOIN sys_permission p on p.id =srp.pid
    where u.id=#{userId}
  </select>

</mapper>
```

**HomeController.java**

```
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "Hi~欢迎");
        return "home";
    }
    @RequestMapping("/login")
    public  String login(){
        return "login";
    }
    @RequestMapping("/admin")
    @ResponseBody
    public String hello(){
        return "hello admin";
    }
}
```

- **5.创建视图资源**

**创建login.html**

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
    <meta content="text/html;charset=UTF-8"/>
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="plugins/bootstrap/dist/css/bootstrap.min.css"/>
    <style type="text/css">
        body {
            padding-top: 50px;
        }
        .starter-template {
            padding: 40px 15px;
            text-align: center;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Spring Security演示</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a th:href="@{/}"> 首页 </a></li>

            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
<div class="container">

    <div class="starter-template">
        <!--/*@thymesVar id="logout" type=""*/-->
        <p th:if="${param.logout}" class="bg-warning">已成功注销</p><!-- 1 -->
        <p th:if="${param.error}" class="bg-danger">有错误，请重试</p> <!-- 2 -->
        <h2>使用账号密码登录</h2>
        <form name="form" th:action="@{/login}" action="/login" method="POST"> <!-- 3 -->
            <div class="form-group">
                <label>账号</label>
                <input type="text" class="form-control" name="username" value="" placeholder="账号" />
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" class="form-control" name="password" placeholder="密码" />
            </div>
            <input type="submit" id="login" value="Login" class="btn btn-primary" />
        </form>
    </div>

</div>

</body>
</html>
```

**创建home.html**

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
    <meta content="text/html;charset=UTF-8"/>
    <title sec:authentication="name"></title>
    <link rel="stylesheet" type="text/css" th:href="@{plugins/bootstrap/dist/css/bootstrap.min.css}" />
    <style type="text/css">
        body {
            padding-top: 50px;
        }
        .starter-template {
            padding: 40px 15px;
            text-align: center;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Spring Security演示</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a th:href="@{/}"> 首页 </a></li>
                <li><a th:href="@{/admin}"> admin </a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>


<div class="container">

    <div class="starter-template">
        <h1 th:text="${msg}"></h1>

        <div sec:authorize="hasRole('ROLE_HOME')"> <!-- 用户类型为ROLE_ADMIN 显示 -->
            <p class="bg-info" th:text="${msg}">你好</p>
        </div>
        <div sec:authorize="hasRole('ROLE_ADMIN')"> <!-- 用户类型为ROLE_ADMIN 显示 -->
            <p class="bg-info">恭喜您,您有 ROLE_ADMIN 权限 </p>
        </div>

        <form th:action="@{/logout}" method="post">
            <input type="submit" class="btn btn-primary" value="注销"/>
        </form>
    </div>

</div>

</body>
</html>
```

------

OK！Security集成完毕，启动服务，访问一下吧。



![img](https://upload-images.jianshu.io/upload_images/11009501-e74ba64dded301af.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)

image.png

输入用户名：admin 密码：admin 成功登录到home界面：





![img](https://upload-images.jianshu.io/upload_images/11009501-cbdfea0607635499.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)

image.png



点击admin按钮则顺利跳转到admin页面。

假如注销后登录其它用户 ：用户名：yu 密码：123，则点击admin按钮提示权限不足无法进入admin页面，说明集成Security权限管理成功。

[^参考简书]:[![96](https://upload.jianshu.io/users/upload_avatars/11009501/3d5945d3-8752-422a-a8d2-193f36e6b061.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96)](https://www.jianshu.com/u/460839314f76) 作者Codingyu 关注 0.4 2018.05.22 22:11 字数 824 阅读 3537评论 2喜欢 23

