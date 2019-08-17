package com.boot.security.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class MyAuthenticationFilter  extends AbstractAuthenticationProcessingFilter {

    public MyAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }



    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String userName = (String) req.getSession().getAttribute("username");
        if("test".equals(userName)){
            super.unsuccessfulAuthentication(req, res,new InsufficientAuthenticationException("输入的验证码不正确"));
        }
       // super.unsuccessfulAuthentication(req, res,new InsufficientAuthenticationException("输入的验证码不正确"));
        chain.doFilter(request, response);
    }
}
