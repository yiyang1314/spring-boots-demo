package com.boot.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.security.mapper.PerminsionMapper;
import com.boot.security.mapper.RoleMapper;
import com.boot.security.mapper.UserMapper;
import com.boot.security.pojo.SysPermission;
import com.boot.security.pojo.SysRole;
import com.boot.security.pojo.SysUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootsListSecurityApplicationTests {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PerminsionMapper perminsionMapper;
	
	
	@Test
	public void perminsiontest() {
		List <Long>ids=new ArrayList<Long>();
		ids.add(1l);
		ids.add(3l);

		//		SysPermission per=new SysPermission();
//		per.setCode("insert");
		List<String> list = perminsionMapper.findPermiss(createInSql(ids));
		System.out.println(list);
	}
	
	
	private String createInSql(List <Long>ids){
		if(ids.size()<=0) return null;
		String in="( ";
		for(Long id:ids) {
			in=in+id+" ,";
		}
		System.out.println(in);
		String sql=in.substring(0, in.lastIndexOf(",")-1)+")";
		System.out.println(sql);
		return sql;
		
	}
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void rolestest() {
		SysRole role=new SysRole();
		role.setRoleName("admin");
		List<SysPermission> list =new ArrayList<SysPermission>();
		
		role.setPermissionList(list );
		System.out.println(roleMapper.save(role));
	}
	
	
	@Test
	public void test() {
		List<Long>  n=userMapper.findRoles(1L);
		System.out.println(n);
	}
	
	
	@Test
	public void savetest() {
		SysUser entity =new SysUser();
		entity.setUserName("manager");
		entity.setPassword("123456");
	//	entity.setRoleList(lists() );
		SysUser n=userMapper.save(entity);
		System.out.println(n);
	}
	
	
	
	
	private List<SysRole> lists(){
		List<SysRole> roleList =new ArrayList<SysRole>();
		SysRole e=new SysRole();
		roleList.add(e);

		
		e.setRoleId(1l);
		e.setRoleName("manager");
		e.setPermissionList(list(1));		

		
		roleList.add(e);	
		e=null;
		e=new SysRole();
		
		e.setRoleId(2l);
		e.setRoleName("admin");
		e.setPermissionList(list(2));	
		
		roleList.add(e);	
		e=null;
		e=new SysRole();
		
		e.setRoleId(3l);
		e.setRoleName("user");
		e.setPermissionList(list(3));	
		
		roleList.add(e);	
		e=null;
		
		return roleList;
	}
	
	private List<SysPermission> list(int i){
		
		List<SysPermission> list =new ArrayList<SysPermission>();
		SysPermission e1=new SysPermission();
		e1.setCode("read");
		e1.setPermId(1l*i);
		list.add(e1);

		SysPermission e2=new SysPermission();
		e2.setCode("write");
		e2.setPermId(1l*i);
		list.add(e2);

		e1=null;
		e1=new SysPermission();
		e1.setCode("save");
		e1.setPermId(3l*i);
		list.add(e1);
		
		e2=null;
		e2=new SysPermission();
		e2.setCode("find");
		e2.setPermId(4l*i);
		list.add(e2);
		
		return list;
	}
	
	@Test
	public void testdel() {
		long n=userMapper.count();
		System.out.println(n);
	}
	
	
	@Test
	public void testfind() {
		long n=userMapper.count();
		System.out.println(n);
	}

}
