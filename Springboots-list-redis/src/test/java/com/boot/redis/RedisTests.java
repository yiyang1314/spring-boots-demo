package com.boot.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.redis.pojo.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		stringRedisTemplate.opsForValue().set("name", "相互");
		Assert.assertEquals("相互", stringRedisTemplate.opsForValue().get("name"));
		System.out.println(stringRedisTemplate.opsForValue().get("name"));
	}

	@Test
	public void testObj() throws Exception {
		Person user = new Person();
		ValueOperations<String, Person> operations = redisTemplate.opsForValue();
		operations.set("com.boot.redis", user);
		operations.set("com.boot.redis", user, 1, TimeUnit.SECONDS);
		Thread.sleep(1000);
		// redisTemplate.delete("com.neo.f");
		boolean exists = redisTemplate.hasKey("com.boot.redis");
		if (exists) {
			System.out.println("exists is true");
		} else {
			System.out.println("exists is false");
		}
		// Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
	}

}
