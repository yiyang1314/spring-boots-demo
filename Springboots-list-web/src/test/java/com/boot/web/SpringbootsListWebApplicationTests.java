package com.boot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.web.autoconfoguration.UserProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(SpringbootsListWebApplication.class)
public class SpringbootsListWebApplicationTests {
	
	@Autowired
	private UserProperties userProperties;
	@Test
	public void testUser() {
		System.out.println(userProperties);
	}

}
