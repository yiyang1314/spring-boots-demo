package com.boot.tmplate;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.boot.tmplate.mapper.UserMapper;
import com.boot.tmplate.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration

public class SpringbootsListTmplateApplicationTests {
	@Autowired
	private UserMapper userMapper;
	@Test
	public void test1() {
		int i=userMapper.selectCount(null);
		System.out.println(i);
		List<User> list = userMapper.selectAll();
		list.forEach(System.out::println);
	}

}
