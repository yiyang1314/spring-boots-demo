package com.boot.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.web.autoconfoguration.UserProperties;
import com.boot.web.pojo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Api(description="API测试接口",value="user")
public class UserController {
	@Autowired
	private UserProperties userProperties;
	@ApiOperation(value="查找用户接口",notes="获取所有用户信息")
       @RequestMapping(value="findUser",method=RequestMethod.GET)
	public User getSelect() {
		User u=new  User();
		u.setName("小糖糖");
		u.setId(20152203l);
		u.setType(0);
		u.setCtime(new Date());
		System.out.println(u);
		System.out.println(userProperties);
		return u;
	}
}
