package com.boot.mp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.mp.mapper.UserMapper;
import com.boot.mp.pojo.User;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MpMysqlTest {
	@Autowired
private UserMapper userMapper;
	
	@Test
	public void testSelectOne() {
	    User user = userMapper.findByName("楚霸王");
	    System.out.println(user);
	}
	
	@Test
	public void testSelectOnes() {
	    User user = userMapper.findByNameOrAge("楚霸王", 36);
	    System.out.println(user);
	}
	@Test
	public void deleteByName() {
		int user = userMapper.deleteByName("楚霸王");
		 System.out.println(user);
	}

	@Test
	public void updateID() {
		  User user=new User();
		  user.setAge(99);
		  user.setId(12l);
		  user.setName("刘备");
		  user.setEmail("tangyang@163.com");
		int count = userMapper.addUser(user);;
		 System.out.println(count);
	}
	
	@Test
	public void addUser() {
		  User user=new User();
		  user.setAge(32);
		  user.setId(12l);
		  user.setName("刘备");
		  user.setEmail("tang@126.com");
		int count = userMapper.addUser(user);;
		 System.out.println(count);
	}
}
