package com.boot.mp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.mp.mapper.UserMapper;
import com.boot.mp.pojo.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class MPTest {
      @Autowired
    private UserMapper userMapper;
    	
    	@Test
    	public void testSelectOne() {
    	    User user = userMapper.selectById(1L);
    	    System.out.println(user);
    	}
    	
    	@Test
    	public void testInsert() {
    	    User user = new User();
    	    user.setName("大阪威久");
    	    user.setAge(36);
    	    user.setEmail("yiyang@qq.com");
    	    int c=userMapper.insert(user);
    	    System.out.println(c);
    	    // 成功直接拿会写的 ID
    	    user.getId();
    	}
    	
    	@Test
    	public void testDelete() {
    	    assertThat(userMapper.deleteById(3L)).isGreaterThan(0);
    	    assertThat(userMapper.delete(new QueryWrapper<User>()
    	            .lambda().eq(User::getName, "Tom"))).isGreaterThan(0);
    	}
    	
    	
    	@Test
    	public void testUpdate() {
    	    User user = userMapper.selectById(2);
    	    assertThat(user.getAge()).isEqualTo(20);
    	    assertThat(user.getName()).isEqualTo("Jack");

    	    userMapper.update(
    	            null,
    	            Wrappers.<User>lambdaUpdate().set(User::getEmail, "123@123").eq(User::getId, 2)
    	    );
    	    assertThat(userMapper.selectById(2).getEmail()).isEqualTo("123@123");
    	}
    	
    	@Test
    	public void testSelect() {
    	    List<User> userList = userMapper.selectList(null);
    	    Assert.assertEquals(5, userList.size());
    	    userList.forEach(System.out::println);
    	}
    	
    	@Test
    	public void testPage() {
    	    System.out.println("----- baseMapper 自带分页 ------");
    	    Page<User> page = new Page<>(1, 2);
    	    IPage<User> userIPage = userMapper.selectPage(page, new QueryWrapper<User>()
    	            .gt("age", 6));
    	    assertThat(page).isSameAs(userIPage);
    	    System.out.println("总条数 ------> " + userIPage.getTotal());
    	    System.out.println("当前页数 ------> " + userIPage.getCurrent());
    	    System.out.println("当前每页显示数 ------> " + userIPage.getSize());
    	    System.out.print(userIPage.getRecords());
    	    System.out.println("----- baseMapper 自带分页 ------");
    	}
}