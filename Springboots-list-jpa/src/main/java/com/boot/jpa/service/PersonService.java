package com.boot.jpa.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boot.jpa.pojo.Person;
//@RequestMapping("API/user")
public interface PersonService {
    /**
     * 查询所有人信息
     * @return Person数组
     */
	//@RequestMapping(value="findAll",method = RequestMethod.GET)
	public List<Person> findPersons();
	/**
	 * 根据主键id查询用户信息
	 * @param id 主键id
	 * @return  返回用户信息
	 */
	//@RequestMapping(value="findById",method = RequestMethod.GET)
	public Person findPersonsById(Integer id);
	/**
	 * 根据用户姓名查询用户信息
	 * @param name 用户姓名
	 * @return 返回用户信息
	 */
	//@RequestMapping(value="findByName",method = RequestMethod.GET)
	public Person findByName(String name);
	/**
	 * 更新用户信息
	 * @param person  更新的用户信息
	 * @return 返回更新成功的状态
	 */
	//@RequestMapping(value="update",method = RequestMethod.PUT)
	public  boolean  update(Person person);
	/**
	 * 根据主键id删除用户信息
	 * @param id 主键id
	 * @return 返回删除成功的状态
	 */
	//@RequestMapping(value="delete",method = RequestMethod.DELETE)
	public  boolean  del(Integer id);
	/**
	 * 添加用户信息
	 * @param person
	 * @return 返回添加成功的状态
	 */
	//@RequestMapping(value="insert",method = RequestMethod.POST,path="/API/user/findById")
	public  boolean  save(Person person);
	
	
}
