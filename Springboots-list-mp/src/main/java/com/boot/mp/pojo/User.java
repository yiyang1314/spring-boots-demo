package com.boot.mp.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("ty_user")
public class User implements Serializable{
	@TableId("id")
    private Long id;
	@TableField("name")
    private String name;
	@TableField("age")
    private Integer age;
	@TableField("email")
    private String email;
}