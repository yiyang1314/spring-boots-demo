package com.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.boot.security.service.MyUserDetailsService;
import com.boot.security.utils.MyPasswordEncoder;
/**
 * 通过@EnableWebSecurity注解开启Spring Security的功能 继承WebSecurityConfigurerAdapter，
 * 并重写它的方法来设置一些web安全的细节 configure(HttpSecurity http)方法，
 * 通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。
 * 例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
 *  通过formLogin()定义当需要用户登录时候，转到的登录页面。 
 *  configureGlobal(AuthenticationManagerBuilder auth)方法，
 * 在内存中创建了一个用户，该用户的名称为admin，密码为123456，用户角色为USER。
 * @author tangyang
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        http.authorizeRequests()
                .antMatchers("/", "/login.html").permitAll()  //定义不需要认证就可以访问
               // .antMatchers("/", "/add.html").permitAll()  //定义不需要认证就可以访问               
                .antMatchers("/**").hasRole("admin")    //需要拥有VIP1权限
                .anyRequest().authenticated()      // 其他地址的访问均需验证权限
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 2)
                .and()
                .formLogin()                     //  定义当需要用户登录时候，转到的登录页面
                .loginPage("/login.html")      //  登录页
                .defaultSuccessUrl("/admin")
                .failureUrl("/login.html").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/index");
        
    }  
//        http
//        // 开启跨域共享
//        .cors()
//        .and()
//        // 跨域伪造请求限制.无效
//        .csrf().disable();
//
//http
//        /*
//        异常处理
//        默认 权限不足  返回403，可以在这里自定义返回内容
//         */
//        .exceptionHandling()
//        .accessDeniedHandler(new DefinedAccessDeniedHandler())
//        .authenticationEntryPoint(new DefinedAuthenticationEntryPoint());
//
//http
//        /**
//         *权限验证配置项
//         */
//        .authorizeRequests()
//        .accessDecisionManager(accessDecisionManager())
//        .withObjectPostProcessor(new DefindeObjectPostProcessor());
//
//http
//        // 开启授权认证
//        .authorizeRequests()
//        // 需要授权访问的
//        .antMatchers(AUTH_URL_REG).authenticated()
//        // OPTIONS预检请求不处理
//        .antMatchers(HttpMethod.OPTIONS).permitAll()
//        // 其他请求不处理
//        .anyRequest().permitAll();
//
//http
//        .logout()
//        .logoutUrl(LOGOUT_URL)
//        .invalidateHttpSession(true)
//        .invalidateHttpSession(true)
//        .logoutSuccessHandler(new DefinedLogoutSuccessHandler());
//
//http
//        // 实现 json 登录
//        .addFilterAt(getJsonFilter(super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
             .withUser("admin").password("123456").roles("admin")
       .and()
       .withUser("manager").password("123456").roles("admin","manager","user");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源忽略认证
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 配置登录验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return new BCryptPasswordEncoder();
        return new MyPasswordEncoder();
    }


}
