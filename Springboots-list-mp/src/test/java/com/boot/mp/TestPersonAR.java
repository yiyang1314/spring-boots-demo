package com.boot.mp;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.mp.pojo.Person;
import com.boot.mp.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class TestPersonAR {

	
	@Test
	public void testSelectOne() {
		Person p = new Person();
		p.selectAll().forEach(System.out::println);
//	       Assert.assertEquals("ab@c.c", new User().setId(1L).selectById().getEmail());
//	        User user = new User().selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
//	        Assert.assertEquals("Jack", user.getName());
//	        Assert.assertTrue(3 == user.getAge());
		print(p.selectList(new QueryWrapper<Person>().lambda().gt(Person::getAge,31)));
	}
	
	
	 @Test
	    public void cUpdate() {
	      ///  Assert.assertTrue(new Person().setId(5).updateById());
	        Assert.assertTrue(true);
	        new Person().update(new UpdateWrapper<Person>().lambda()
                    .set(Person::getAge, 23).eq(Person::getId, 5));
	    }
	
	
	 @Test
	    public void aInsert() {
		 Person user = new Person();
	        user.setName("苍井");
	        user.setAge(29);
	        user.setSalary(2825.36);
	        user.setId(9);
	        //boolean boo=user.updateById();
	       Assert.assertTrue(user.insert());
	        // 成功直接拿会写的 ID
	        System.err.println("\n插入成功 ID 为：" + user.getId());
	    }


	 @Test
	    public void sert() {
		 Person user = new Person();
	        //user.setName("苍井空65656");
//	       user.setAge(532);
//	       user.setSalary(3625.36);
	       // user.setId(4);
	       //print(user.selectAll());
	       System.out.println(user.selectOne(new QueryWrapper<Person>().lambda()
	    		   .eq(Person::getAge, 532)
	    		   .eq(Person::getName, "苍井空")
	    		   ));
	       user = new Person();
	       user.setAge(25);
	       user.setName("上原亚衣");
	        // 成功直接拿会写的 ID
	      System.out.println("\n插入成功 ID 为：" +user.selectOne(null));
	    }

	    @Test
	    public void bDelete() {
	       // Assert.assertTrue(new Person().setId(3).deleteById());
	        Assert.assertTrue(new Person().delete(new QueryWrapper<Person>()
	                .lambda().eq(Person::getName, "Sandy")));
	    }

	 
	private <T> void print(List<T> list) {
	    if (!CollectionUtils.isEmpty(list)) {
	        list.forEach(System.out::println);
	    }
	}
	
	@Test
	public void findAllPagesCombox() {
		// 分页查询 10 条姓名为‘张三’的用户记录
		
		
		 Person user = new Person();
		
			List<Person> PAGES= user.selectPage(
		        new Page<Person>(1, 10),
		        new QueryWrapper<Person>().gt("salary", 3700).orderByAsc("salary").orderByDesc("age")
		        //new QueryWrapper<Person>().between("salary", 3977.25, 9525.36).groupBy("salary")
		       // new QueryWrapper<Person>().between("salary", 1777.25, 9525.36).or().gt("id", 4)
		        //new QueryWrapper<Person>().lambda().gt(Person::getAge,27).in(Person::getId, Arrays.asList(1.2,4,6,3,5))
		).getRecords();
			System.out.println(PAGES.size());
	        print(PAGES);
	}
}
