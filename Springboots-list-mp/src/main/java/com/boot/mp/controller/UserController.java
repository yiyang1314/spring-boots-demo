package com.boot.mp.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.boot.mp.mapper.UserMapper;
import com.boot.mp.pojo.User;
import com.boot.mp.result.ResultMsg;
import com.boot.mp.result.ResultSet;
import com.boot.mp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(description="MP-mysql-API测试接口",value="用户API")
@RequestMapping("API/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
    private UserMapper userMapper;
	/**
     * 根据用户主键ID查询用户信息
     * @return  返回用户信息
     */
	@RequestMapping(value="findById",method = RequestMethod.GET)
	@ApiOperation(value="用户主键ID查询",notes="根据用户主键ID查询用户信息")
	public User findById(Long id) {
		User u = userMapper.selectById(id);
		return u;
	}
	/**
     *  按照姓名查询用户
     * @return User 用户
     */
	@RequestMapping(value="findByName",method = RequestMethod.GET)	
	@ApiOperation(value="按照姓名查询用户接口",notes="按照姓名查询用户信息")
	public User findByName(@RequestParam("name") String name) {
		return userMapper.findByName(name);
		
	}
	/**
     *  按照姓名或者年龄查询用户
     * @return User 用户
     */
	@RequestMapping(value="findByNameOrAge",method = RequestMethod.GET)
	@ApiOperation(value="按照姓名或者年龄查询接口",notes="按照姓名或者年龄查询用户信息")
	public User findByNameOrAge(String name,Integer age) {
		return userMapper.findByNameOrAge(name, age);
	}
	/**
     * 查询所有人信息
     * @return Person数组
     */
	@RequestMapping(value="findAll",method = RequestMethod.GET)
	@ApiOperation(value="查询所有用户接口",notes="查询用户列表信息")
	public List<User> findAll() {
		return userMapper.selectList(null);

	}
	/**
     * 根据用户姓名删除用户信息
     * @return Person数组
     */
	@RequestMapping(value="deleteByName",method = RequestMethod.DELETE)	
	@ApiOperation(value="按姓名删除接口",notes="按姓名删除用户信息")
	public ResultSet deleteByName(String name) {
		int boo=userMapper.deleteByName(name);
		return new ResultSet(boo>0?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo>0);
		
	}
	/**
     * 根据ID数组删除多个用户信息
     * @return Person数组
     */
	@RequestMapping(value="deleteArrays",method = RequestMethod.DELETE)
	@ApiOperation(value="分组删除用户接口",notes="分组删除用户信息")
	public ResultSet deleteArrays(Long []ids) {
		int boo = userMapper.deleteBatchIds(Arrays.asList(ids));
		return new ResultSet(boo>0?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo>0);
	}
	
	/**
     * 更新用户信息
     * @return ResultSet
     */
	@RequestMapping(value="update",method = RequestMethod.PUT)
	@ApiOperation(value="更新用户接口",notes="更新用户信息")
	public ResultSet update(User user) {
		int boo = userMapper.updateByUserID(user);
		return new ResultSet(boo>0?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo>0);
	}

	/**
     * 添加用户信息
     * @return Person数组
     */
	@RequestMapping(value="insert",method = RequestMethod.POST)
	@ApiOperation(value="添加用户接口",notes="添加用户信息")
	public ResultSet insert (User user) {
		int boo =userMapper.addUser(user);
		return new ResultSet(boo>0?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo>0);
	}
}
