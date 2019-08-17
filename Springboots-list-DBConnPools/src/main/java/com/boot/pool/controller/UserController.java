package com.boot.pool.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.pool.mapper.UserMapper;
import com.boot.pool.pojo.User;
import com.boot.pool.result.ResultSet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@RestController
@Api(description="pool-API测试接口",value="用户API")
@RequestMapping("API/user")
public class UserController {

	
	@Autowired
    private UserMapper userMapper;

	/**
     *  按照姓名查询用户
     * @return User 用户
     */
	@RequestMapping(value="findByName",method = RequestMethod.GET)	
	@ApiOperation(value="按照姓名查询用户接口",notes="按照姓名查询用户信息")
	public User findByName(@RequestParam("name") String name) {
		System.out.println(name);
		User u = new User();
		u.setName(name);
		return userMapper.selectOne(u);
		
	}

	/**
     * 查询所有人信息
     * @return Person数组
     */
	@RequestMapping(value="findAll",method = RequestMethod.GET)
	@ApiOperation(value="查询用户接口",notes="查询用户列表信息")
	public List<User>  findAll(Long []ids,String name) {
		System.out.println("--------------------------------");
		User user = new User();
		user.setName(name);
		Example example=new Example(User.class);
		Criteria criteria=example.createCriteria();
		System.out.println(user);
		if(user!=null) {
			if(user.getAge()!=null&&user.getAge().intValue()>0) {
				criteria.andEqualTo("age", user.getAge());
			}
			if(user.getName()!=null&&user.getName().length()>0) {
				criteria.andLike("name","%"+user.getName()+"%");
			}
			if(user.getId()!=null&&user.getId().longValue()>0) {
				criteria.andIn("id",CollectionUtils.arrayToList(ids));
			}
			if(user.getEmail()!=null&&user.getEmail().length()>0) {
				criteria.andLike("email", "%"+user.getEmail());
			}
		}
			List<User> list=userMapper.selectByExample(example);
			list.forEach(System.out::println);
		return list;

	}

}
