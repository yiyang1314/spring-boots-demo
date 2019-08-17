package com.boot.mp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.mp.mapper.PersonMapper;
import com.boot.mp.pojo.Person;
import com.boot.mp.pojo.Person;
import com.boot.mp.result.ResultMsg;
import com.boot.mp.result.ResultSet;
import com.boot.mp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(description="MP-AR测试接口",value="用户API")
@RequestMapping("AR-API/Person")
@Slf4j
public class PersonController {

	@Autowired
	private PersonMapper personMapper;
	/**
     * 根据用户主键ID查询用户信息
     * @return  返回用户信息
     */
	@RequestMapping(value="findById",method = RequestMethod.GET)
	@ApiOperation(value="用户主键ID查询",notes="根据用户主键ID查询用户信息")
	public Person findById(Long id) {
		Person pp=new Person();
		Person u = pp.selectById(id);
		return u;
	}
	/**
     *  按照姓名查询用户
     * @return Person 用户
     */
	@RequestMapping(value="findByName",method = RequestMethod.GET)	
	@ApiOperation(value="按照姓名查询用户接口",notes="按照姓名查询用户信息")
	public Person findByName(@RequestParam("name") String name) {
		Person pp=new Person();
		pp.setName(name);
		Wrapper<Person> queryWrapper =new QueryWrapper<Person>().eq("name", name);
		return pp.selectOne(queryWrapper);
		
	}
	/**
     *  按照姓名或者年龄查询用户
     * @return Person 用户
     */
	@RequestMapping(value="findByNameOrAge",method = RequestMethod.GET)
	@ApiOperation(value="按照姓名或者年龄查询接口",notes="按照姓名或者年龄查询用户信息")
	public Person findByNameOrAge(String name,Integer age) {
		Person pp=new Person();
		pp.setName(name);
		pp.setAge(age);
		Wrapper<Person> queryWrapper =new QueryWrapper<Person>().eq("name", name).gt("age", 35);
		return pp.selectOne(queryWrapper);
	}
	/**
     * 查询所有人信息
     * @return Person数组
     */
	@RequestMapping(value="findAll",method = RequestMethod.GET)
	@ApiOperation(value="查询所有用户接口",notes="查询用户列表信息")
	public List<Person> findAll() {
		Person pp=new Person();
		// 查询姓名为‘张三’的所有用户记录
		return pp.selectList(null);

	}
	/**
     * 根据用户姓名删除用户信息
     * @return Person数组
     */
	@RequestMapping(value="deleteByName",method = RequestMethod.DELETE)	
	@ApiOperation(value="按姓名删除接口",notes="按姓名删除用户信息")
	public ResultSet deleteByName(String name) {
		Person pp=new Person();
		pp.setName(name);
		boolean boo=pp.deleteById();
		return new ResultSet(boo?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo);
		
	}
	/**
     * 根据ID数组删除多个用户信息
     * @return Person数组
     */
	@RequestMapping(value="deleteArrays",method = RequestMethod.DELETE)
	@ApiOperation(value="分组删除用户接口",notes="分组删除用户信息")
	public ResultSet deleteArrays(Long []ids) {
		int boo = personMapper.deleteBatchIds(Arrays.asList(ids));
		return new ResultSet(boo>0?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo>0);
	}
	
	/**
     * 更新用户信息
     * @return ResultSet
     */
	@RequestMapping(value="update",method = RequestMethod.PUT)
	@ApiOperation(value="更新用户接口",notes="更新用户信息")
	public ResultSet update(Person person) {
		System.out.println(person);
		Map params =new HashMap();
		Wrapper<Person> updateWrapper =new UpdateWrapper<Person>().lambda();
		boolean boo = person.updateById();
		return new ResultSet(boo?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo);
	}

	/**
     * 添加用户信息
     * @return Person数组
     */
	@RequestMapping(value="insert",method = RequestMethod.POST)
	@ApiOperation(value="添加用户接口",notes="添加用户信息")
	public ResultSet insert (@RequestBody Person person) {

		boolean boo =person.insert();
		return new ResultSet(boo?ResultMsg.SUCCESS.getDesc():ResultMsg.FAILD.getDesc(),boo);
	}
	
	@RequestMapping(value="fingPageAll",method = RequestMethod.GET)
	@ApiOperation(value="简单分页接口",notes="添加用户信息")
	public List<Person> fingPageAll(@RequestParam("page")int page,@RequestParam("pageSize")int pageSize,@RequestBody Person person) {
		 System.out.println("fingPageAll"+person);
		// 分页查询 10 条姓名为‘张三’的用户记录
		 List<Person> list=person.selectPage(
		        new Page<Person>(page, pageSize),
		        new QueryWrapper<Person>().lambda().gt(Person::getAge, person.getAge())
		).getRecords();
		 System.out.println(list.size());
		 list.forEach(System.out::println);
		return list;
	}
	@RequestMapping(value="findAllPagesCombox",method = RequestMethod.GET)
	@ApiOperation(value="复杂接口",notes="添加用户信息")
	public List<Person> findAllPagesCombox(@RequestParam("page")int page,@RequestParam("pageSize")int pageSize,@RequestBody Person person) {
		
		System.out.println("findAllPagesCombox"+person);
		// 分页查询 10 条姓名为‘张三’的用户记录
		List<Person> list=person.selectPage(
		        new Page<Person>(page, pageSize),
		        new QueryWrapper<Person>().gt("salary", person.getSalary()).orderByAsc("salary").orderByDesc("age")
		).getRecords();
		 System.out.println(list.size());
		list.forEach(System.out::println);
		return list;
	}
	
}
