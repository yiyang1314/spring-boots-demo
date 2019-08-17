package com.boot.pool.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="ty_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
	private Long id;

    private String name;

    private Integer age;

    private String email;
}