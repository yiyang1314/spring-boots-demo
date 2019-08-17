package com.boot.shiro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.shiro.mapper.UserMapper;
import com.boot.shiro.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootsListShiroApplicationTests {
	
	@Autowired
	private UserMapper userMapper;
	@Test
	public void test() {
		long n=userMapper.count();
		System.out.println(n);
	}
	
	@Test
	public void insert() {
		User u=new User();
		u.setName("cacao");
		u.setPassword("123456");
		u.setUsername("user");
		u.setSalt("tyf");
		u.setState(( byte)0);

		System.out.println(userMapper.save(u));
	}
	
	
	@Test
	public void del() {
		User u=new User();
		u.setName("cacao");
		u.setPassword("123456");
		u.setUsername("user");
		u.setSalt("tyf");
		u.setState(( byte)0);
		userMapper.delete(u);

	}
	
	
	@Test
	public void update() {
		long n=userMapper.count();
		System.out.println(n);
	}
	
	@Test
	public void find() {

		System.out.println(userMapper.findByUsername("admin"));
	}

}
