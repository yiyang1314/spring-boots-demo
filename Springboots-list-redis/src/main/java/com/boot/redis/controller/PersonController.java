package com.boot.redis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.boot.redis.maper.PersonMapper;
import com.boot.redis.pojo.Person;
import com.boot.redis.result.ResultMsg;
import com.boot.redis.result.ResultSet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(description="API测试接口",value="person")
@RequestMapping("API/person")
public class PersonController {
	

	private ResultMsg resultMsg;
	 
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private PersonMapper personMapper;
	

	    @Cacheable(value="user-key")
	@RequestMapping(value="findOne",method = RequestMethod.GET)
	@ApiOperation(value="查找用户接口",notes="获取所有用户信息")
	  public Person getPerson() {
	    	Person user=new Person();
	        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
	        return user;
	    }
	

	/**
     * 查询所有人信息
     * @return Person数组
     */
	@RequestMapping(value="findAll",method = RequestMethod.GET)
	@ApiOperation(value="查找用户接口",notes="获取所有用户信息")
	public List<Person> findPersons(HttpSession session){
		List<Person> list=null;
		ValueOperations<String, List<Person>> operations = redisTemplate.opsForValue();
		list=(List)operations.get("com.boot.redis");
		if(list==null) {
			 list= personMapper.findAll();
				operations.set("com.boot.redis", list);
		}
		list.forEach(t->System.out.println(t));
		session.setAttribute("uid", "JSSUID-64484848-5949494-1545SASX5454-151");
		System.out.println(session.getAttribute("uid"));
		return list;
		
	}
	/**
	 * 根据主键id查询用户信息
	 * @param id 主键id
	 * @return  返回用户信息
	 */
	@RequestMapping(value="findById",method = RequestMethod.GET)
	@ApiOperation(value="主键ID用户接口",notes="获取用户信息")
	public Person findPersonsById(@RequestParam("id") Integer id){
		Person p1=personMapper.findById(id).get();
		System.out.println(p1);
		return p1;
		
	}
	/**
	 * 根据用户姓名查询用户信息
	 * @param name 用户姓名
	 * @return 返回用户信息
	 */
	@RequestMapping(value="findByName",method = RequestMethod.GET)
	@ApiOperation(value="姓名用户接口",notes="获取用户信息")
	public Person findByName(@RequestParam("name") String name){
		Person p1=personMapper.findByName(name);
		System.out.println(p1);
		return p1;
		
	}
	/**
	 * 更新用户信息
	 * @param person  更新的用户信息
	 * @return 返回更新成功的状态
	 */
	@RequestMapping(value="update",method = RequestMethod.PUT)
	@ApiOperation(value="更新用户接口",notes="更新用户信息")
	public  ResultSet  update(Person person,HttpServletRequest request){
		personMapper.save(person);
		findPersons(request.getSession());
		return new ResultSet("更新操作成功",true);
		
	}
	/**
	 * 根据主键id删除用户信息
	 * @param id 主键id
	 * @return 返回删除成功的状态
	 */
	@RequestMapping(value="delete",method = RequestMethod.DELETE)
	@ApiOperation(value="删除用户接口",notes="删除用户信息")
	public  ResultSet  del(@RequestParam("id") Integer id,HttpServletRequest request){
		personMapper.deleteById(id);
		findPersons(request.getSession());
		return new ResultSet("删除操作成功",true);
		
	}
	/**
	 * 添加用户信息
	 * @param person
	 * @return 返回添加成功的状态
	 */
	@RequestMapping(value="insert",method = RequestMethod.POST)
	@ApiOperation(value="添加用户接口",notes="添加用户信息")
	public  ResultSet  save(@RequestBody Person person,HttpServletRequest request){
		personMapper.save(person);
		findPersons(request.getSession());
		return new ResultSet("添加",true);
	}
}
