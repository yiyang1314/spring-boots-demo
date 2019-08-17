package com.boot.shiro.service;

import com.boot.shiro.pojo.User;

public interface UserService {
	/**通过username查找用户信息;*/
    public User findByUsername(String username);
}
