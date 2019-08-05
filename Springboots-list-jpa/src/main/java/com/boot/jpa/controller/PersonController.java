package com.boot.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.jpa.pojo.Person;
import com.boot.jpa.result.ResultMsg;
import com.boot.jpa.result.ResultSet;
import com.boot.jpa.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(description="API测试接口",value="person")
@RequestMapping("API/person")
public class PersonController {
	private ResultMsg resultMsg;
	 
	@Autowired
	private PersonService personService;
	
	/**
     * 查询所有人信息
     * @return Person数组
     */
	@RequestMapping(value="findAll",method = RequestMethod.GET)
	@ApiOperation(value="查找用户接口",notes="获取所有用户信息")
	public List<Person> findPersons(){
		return personService.findPersons();
		
	}
	/**
	 * 根据主键id查询用户信息
	 * @param id 主键id
	 * @return  返回用户信息
	 */
	@RequestMapping(value="findById",method = RequestMethod.GET)
	@ApiOperation(value="主键ID用户接口",notes="获取用户信息")
	public Person findPersonsById(@RequestParam("id") Integer id){
		return personService.findPersonsById(id);
		
	}
	/**
	 * 根据用户姓名查询用户信息
	 * @param name 用户姓名
	 * @return 返回用户信息
	 */
	@RequestMapping(value="findByName",method = RequestMethod.GET)
	@ApiOperation(value="姓名用户接口",notes="获取用户信息")
	public Person findByName(@RequestParam("name") String name){
		return personService.findByName(name);
		
	}
	/**
	 * 更新用户信息
	 * @param person  更新的用户信息
	 * @return 返回更新成功的状态
	 */
	@RequestMapping(value="update",method = RequestMethod.PUT)
	@ApiOperation(value="更新用户接口",notes="更新用户信息")
	public  ResultSet  update(Person person){
		personService.save(person);
		return new ResultSet("更新操作成功",true);
		
	}
	/**
	 * 根据主键id删除用户信息
	 * @param id 主键id
	 * @return 返回删除成功的状态
	 */
	@RequestMapping(value="delete",method = RequestMethod.DELETE)
	@ApiOperation(value="删除用户接口",notes="删除用户信息")
	public  ResultSet  del(@RequestParam("id") Integer id){
		boolean flg=personService.del(id);
		return new ResultSet("删除操作成功",flg);
		
	}
	/**
	 * 添加用户信息
	 * @param person
	 * @return 返回添加成功的状态
	 */
	@RequestMapping(value="insert",method = RequestMethod.POST)
	@ApiOperation(value="添加用户接口",notes="添加用户信息")
	public  ResultSet  save(@RequestBody Person person){
		personService.save(person);
		return new ResultSet("添加",true);
	}
}
