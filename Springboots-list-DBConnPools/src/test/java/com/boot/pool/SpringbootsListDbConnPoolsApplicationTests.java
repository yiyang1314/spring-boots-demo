package com.boot.pool;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.pool.mapper.UserMapper;
import com.boot.pool.pojo.User;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootsListDbConnPoolsApplicationTests {
	
	@Autowired
    private UserMapper userMapper;
	
	@Test
	public void test1() {
		int n=userMapper.selectCount(null);
		System.out.println(n);
		
		User u = new User();
		u.setName("小唐");
		User u1=userMapper.selectOne(u);
		System.out.println(u1);
		

	}

	@Test
	public void test2() {
		Example example=new Example(User.class);
		Criteria criteria=example.createCriteria();
//		criteria.andEqualTo("name", "曹操");
//		criteria.andGreaterThan("age", 25);
//		criteria.andLessThan("age", 100);
		System.out.println("---------------------------------------------------");
		List<User> list=userMapper.selectByExample(example);
		list.forEach(System.out::println);
	}
	
	
	@Test
	public void test3() {
		Example example=new Example(User.class);
		Criteria criteria=example.createCriteria();
		User user = new User();
		user.setName("曹");
		if(user!=null) {
			if(user.getAge()!=null&&user.getAge().intValue()>0) {
				criteria.andEqualTo("age", user.getAge());
			}
			if(user.getName()!=null&&user.getName().length()>0) {
				criteria.andLike("name","%"+user.getName()+"%");
			}
			if(user.getId()!=null&&user.getId().longValue()>0) {
				criteria.andEqualTo("id", user.getId());
			}
			if(user.getEmail()!=null&&user.getEmail().length()>0) {
				criteria.andLike("email", "%"+user.getEmail());
			}
		}
			List<User> list=userMapper.selectByExample(example);
			list.forEach(System.out::println);
	}
	
	
}
