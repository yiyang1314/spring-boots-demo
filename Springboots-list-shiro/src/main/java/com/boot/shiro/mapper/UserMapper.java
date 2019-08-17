package com.boot.shiro.mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.shiro.pojo.User;
public interface UserMapper extends JpaRepository<User,Long>{
	/**通过username查找用户信息;*/
    public User findByUsername(String username);
}
