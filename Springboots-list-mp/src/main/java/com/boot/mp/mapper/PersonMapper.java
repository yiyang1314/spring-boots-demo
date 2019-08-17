package com.boot.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.mp.pojo.*;

@Mapper 
public interface PersonMapper extends BaseMapper<Person>{
	public List<User> findUser();

             @Select("SELECT * FROM ty_user WHERE name=#{name}")
     	@Results({
	        @Result(property = "id", column = "id"),
	        @Result(property = "name", column = "name"),
	        @Result(property = "age", column = "age"),
	        @Result(property = "email", column = "email")})
    //根据UserName查询User信息
	public User findByName(@Param("name")String name);
	
	@Select("SELECT * FROM ty_user WHERE name=#{name} or age=#{age}")
	@Results({
		        @Result(property = "id", column = "id"),
		        @Result(property = "name", column = "name"),
		        @Result(property = "age", column = "age"),
		        @Result(property = "email", column = "email")})
	public User findByNameOrAge(@Param("name")String name ,@Param("age")Integer age);
	
//	@SelectKey(keyProperty = "id",keyColumn="id",resultType = User.class, before =true, statement = "select replace(uuid(), '-', '-')" )
//	    @Options(keyProperty = "id", useGeneratedKeys = true)
	    @Insert("INSERT INTO ty_user(id,name,age,email)"
	    		+ "VALUES(#{id},#{name},#{age},#{email})")
//	@Results({
//        @Result(property = "id", column = "id"),
//        @Result(property = "name", column = "name"),
//        @Result(property = "age", column = "age"),
//        @Result(property = "email", column = "email")})
    public int addUser(User user);

	@Update("UPDATE ty_user SET name=#{name},age=#{age},email=#{email} WHERE id=#{id}")
	@Results({
		        @Result(property = "id", column = "id"),
		        @Result(property = "name", column = "name"),
		        @Result(property = "age", column = "age"),
		        @Result(property = "email", column = "email")})
    public int updateByUserID(User user);


	@Delete("DELETE FROM ty_user WHERE name=#{name}")
	public int deleteByName(@Param("name") String name);
}
