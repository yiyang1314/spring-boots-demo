
package com.boot.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.jpa.mapper.PersonMapper;
import com.boot.jpa.pojo.Person;
import com.boot.jpa.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {
      @Autowired
	private PersonMapper   personMapper;
	@Override
	public List<Person> findPersons() {
		
		return personMapper.findAll();
	}

	@Override
	public Person findPersonsById(Integer id) {
		Optional<Person> P = personMapper.findById(2);
		return (Person)P.get();
	}

	@Override
	public Person findByName(String name) {
		return personMapper.findByName(name);
	}

	@Override
	public boolean update(Person person) {
		Person count = personMapper.save(person);
		return count==null?true:false;
	}

	@Override
	public boolean del(Integer id) {
		try {
		personMapper.deleteById(id);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean save(Person person) {
		
		try {
			personMapper.save(person);
			}catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		return true;
	}

}
