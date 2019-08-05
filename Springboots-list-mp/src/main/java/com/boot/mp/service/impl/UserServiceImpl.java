package com.boot.mp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.mp.mapper.UserMapper;
import com.boot.mp.pojo.User;
import com.boot.mp.service.UserService;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{

}
