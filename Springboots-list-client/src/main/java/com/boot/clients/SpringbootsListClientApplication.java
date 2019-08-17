package com.boot.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootsListClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootsListClientApplication.class, args);
	}
	@RequestMapping("index")
	public String index() {
		return "<h1>欢欢迎入客户端主页.....</h1>";
	}
}
