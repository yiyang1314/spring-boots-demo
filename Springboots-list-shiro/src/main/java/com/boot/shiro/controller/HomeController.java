package com.boot.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.shiro.config.ShiroConfig2;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {
	
	    @RequestMapping("index")
	    public String index(){
	        return"index";
	    }
	    @RequestMapping("/login")
	    public String login(){
	        return"403";
	    }
	    
	    @PostMapping("/doLogin")
	public String doLogin(String username, String password,Model mode,HttpServletRequest request) {
	    	try {
	    	request.setCharacterEncoding("utf-8");
	    	log.debug("username:   "+username+request.getParameter("username"));
	    	System.out.println("username"+username);
			System.out.println("----------------------------------------------------------");
	    	UsernamePasswordToken token=
					new UsernamePasswordToken(request.getParameter("username"),request.getParameter("password"));
	    	log.debug("UsernamePasswordToken:   "+password);
			Subject s=SecurityUtils.getSubject();
	    		s.login(token);
	    	}catch(AuthorizationException e) {
	    		System.out.println("----------------------------------------------------------");
	    		mode.addAttribute("error", "用户认证失败！");
	    		log.debug("用户认证失败:   ");
	    		return "login";
	    	}catch(Exception e) {
	    		System.out.println("----------------------------------------------------------");
	    		mode.addAttribute("error", "用户认证失败！");

	    		return "login";
	    	}finally {
	    		log.debug("用户认证成功:   "+password);
	    	}
	    	mode.addAttribute("success", "登录成功！");
	    	log.debug("用户认证成功:   "+password);
	    		
	    	return "index";
	    }
	    
	    @RequiresRoles("admin")
	    @PostMapping("/admin")
	    public String admin() {
	    	System.out.println("admin------权限使用者.......");
	    	return "admin";
	    }
	    
	    @RequiresRoles(value= {"admin","user","sang"},logical=Logical.OR)
	    @PostMapping("/user")
	    public String user() {
	    	System.out.println("user------权限使用者.......");
	    	return "user";
	    }
	  
 
	    @RequestMapping("/403")
	    public String unauthorizedRole(){
	        System.out.println("------没有权限-------");
	        return "403";
	    }

}