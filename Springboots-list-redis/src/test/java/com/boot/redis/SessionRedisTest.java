package com.boot.redis;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionRedisTest {
	@Autowired
	private HttpSession session;

	public HttpSession getSession() {
		return session;
	}

	public void session(HttpSession session) {
		this.session = session;
	}
	
	@Test
	public void test() {
		System.out.println(session.getAttribute("uid"));
	}
	
}
