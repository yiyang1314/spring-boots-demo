package com.boot.web.pojo;

//import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class User{
	private  Long id;
	private String name;
	private Date ctime;
	private   Integer age;
	private Integer  type;
}
