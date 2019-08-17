package com.boot.mp.pojo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.Data;

@Data
@TableName
public class Person extends Model<Person> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId
    private Integer id=1;
	@TableField
    private String name;
	@TableField
	private Integer age;
	@TableField
    private Double salary;
	@Override
	  protected Serializable pkVal() {
	      return this.id;
	  }
	
}
