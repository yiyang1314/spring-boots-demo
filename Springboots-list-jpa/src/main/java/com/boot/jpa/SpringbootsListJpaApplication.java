package com.boot.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.boot.jpa")
@EnableAutoConfiguration
public class SpringbootsListJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootsListJpaApplication.class, args);
	}

}
