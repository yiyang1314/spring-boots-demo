package com.boot.security.controller
;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.security.service.MyUserDetailsService;

@Controller
public class IndexController {
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	/**
	 * @PreAuthorize("hasPermission('/perm','perm')")是关键，
	 * 参数1指明了访问该接口需要的url，参数2指明了访问该接口需要的权限。
	 * @return
	 */
	//@ResponseBody
	    @RequestMapping("/admin")
	    @PreAuthorize("hasPermission('/admin','admin')")
	    public String perm(){
	        return "admin";
	    }
	    
	    @RequestMapping("/user")
//	 @Role(value={"admin","manager"})
	    public String perm1(){
	        return "admin";
	    } 
	    @RequestMapping("/login")
	    public String login(String username, String password) {
	    	UserDetails de = myUserDetailsService.loadUserByUsername(username);
	    	
			return "admin";
	    }

		@RequestMapping("/index")
		//@Role(value={"admin","manager"})
		public String index2(){
		    return "/user";
		}
}
