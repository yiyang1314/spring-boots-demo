package com.boot.redis.maper;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.redis.pojo.Person;




public interface PersonMapper extends JpaRepository<Person, Integer>{ 
	public Person findByName(String name);
}