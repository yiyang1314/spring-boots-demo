package com.boot.jpa.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.jpa.pojo.Person;


public interface PersonMapper extends JpaRepository<Person, Integer>{ 
	public Person findByName(String name);
}