package com.boot.tmplate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.tmplate.mapper.UserMapper;
import com.boot.tmplate.pojo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(description="API测试接口",value="用户API")
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserMapper userMapper;
	/**
     * 根据用户主键ID查询用户信息
     * @return  返回用户信息
     */
	@RequestMapping(value="index",method = RequestMethod.GET)
	@ApiOperation(value="引擎模板都适用",notes="根据用户主键ID查询用户信息")
	public String index(Model mode) {
		User user=userMapper.selectByPrimaryKey(6);
		mode.addAttribute("user", user);

		return "index";
	}
	
	@RequestMapping(value="login",method = RequestMethod.GET)
	@ApiOperation(value="引擎模板都适用",notes="根据用户主键ID查询用户信息")
	public String index2(String name,Model mode) {
		User record=new User();
		record.setName(name);
		List<User> list =userMapper.select(record);
		mode.addAttribute("list", list);
		return "login";
	}
	
	@RequestMapping(value="show",method = RequestMethod.GET)
	@ApiOperation(value="用户主键ID查询",notes="根据用户主键ID查询用户信息")
	public String index3(HttpServletRequest request) {
		List<User> list = userMapper.selectAll();
		request.setAttribute("list", list);
		return "show";
	}
	
	@RequestMapping(value="list",method = RequestMethod.GET)
	@ApiOperation(value="适用于jsp接口",notes="根据用户主键ID查询用户信息")
	public String list(Model mode,HttpServletRequest request) {
		
		List<User> list = userMapper.selectAll();
		request.setAttribute("list", list);
		mode.addAttribute("list", list);
		//ModelMap map
		return "list";
	}
	
	@RequestMapping(value="findAll",method = RequestMethod.GET)
	@ApiOperation(value="适用于jsp接口",notes="根据用户主键ID查询用户信息")
	public String lists(HttpServletRequest request,Model mode) {

			List<User> list = userMapper.selectAll();
			list.forEach(System.out::println);
			mode.addAttribute("list", list);
			request.setAttribute("list", list);
		return "list";
	}
	
	
	@RequestMapping(value="findsl",method = RequestMethod.GET)
	@ApiOperation(value="适用于jsp接口",notes="根据用户主键ID查询用户信息")
	public String listlsfff(HttpServletRequest request, Model mode) {

			List<User> list = userMapper.selectAll();
			System.out.println(list.size());
			mode.addAttribute("list", list);
			request.setAttribute("list", list);
		return "list";
	}
}
