package com.tang.boot.pojo;

import java.util.Date;

public class User {
	private  Long id;
	private String name;
	private Date ctime;
	private   Integer age;
	private Integer  type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public User(Long id, String name, Date ctime, Integer age, Integer type) {
		super();
		this.id = id;
		this.name = name;
		this.ctime = ctime;
		this.age = age;
		this.type = type;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", ctime=" + ctime + ", age=" + age + ", type=" + type + "]";
	}
	
}
