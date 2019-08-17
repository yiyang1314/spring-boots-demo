package com.boot.tmplate.mapper;



import com.boot.tmplate.pojo.User;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;
@MapperScan(basePackages = "com.boot.tmplate.mapper")
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {

}
