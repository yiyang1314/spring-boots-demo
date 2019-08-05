package com.boot.jpa;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.jpa.mapper.PersonMapper;
import com.boot.jpa.pojo.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootsListJpaApplicationTests {
	
   	@Autowired
private PersonMapper   personMapper;
   	
	@Test
	public void testInsertPerson() {
		Person P = new Person();
		P.setAge(2127);
		//P.setId(20183625);
		P.setName("刘洵");
		P.setSalary(100000.25);
		System.out.println(P);
		personMapper.save(P);
	}
	@Test
	public void testFindById() {
		System.out.println(personMapper.findById(2));
	}
       	
    	@Test
    	public void testFindAllPersons() {
    		List<Person> p = personMapper.findAll() ;

//    		Assert.assertEquals(9, userRepository.findAll().size());
//    		Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb", "cc@126.com").getNickName());
    		p.forEach(e->System.out.println(e));
    	}
    	
    	@Test
    	public void testFindNamelPersons() {
    		Person p = personMapper.findByName("小白") ;
    		System.out.println(p);
    	}
    	
    	@Test
    	public void testUpdatePerson() {
    		Person P = new Person();
    		P.setAge(189);
    		P.setId(2);
    		P.setName("仁科百华");
    		P.setSalary(10020.25);
    		System.out.println(P);
    		personMapper.save(P);
    	}
    	
//    	第一种方法引用的类型是构造器引用，语法是Class::new，或者更一般的形式：Class<T>::new。注意：这个构造器没有参数。
//
//    	final Car car = Car.create( Car::new );
//    	final List< Car > cars = Arrays.asList( car );
//    	第二种方法引用的类型是静态方法引用，语法是Class::static_method。注意：这个方法接受一个Car类型的参数。
//
//    	cars.forEach( Car::collide );
//    	第三种方法引用的类型是某个类的成员方法的引用，语法是Class::method，注意，这个方法没有定义入参：
//
//    	cars.forEach( Car::repair );
//    	第四种方法引用的类型是某个实例对象的成员方法的引用，语法是instance::method。注意：这个方法接受一个Car类型的参数：
//
//    	final Car police = Car.create( Car::new );
//    	cars.forEach( police::follow );
}
