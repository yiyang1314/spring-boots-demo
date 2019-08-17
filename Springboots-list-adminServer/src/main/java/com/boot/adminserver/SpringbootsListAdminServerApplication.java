package com.boot.adminserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
@RestController
public class SpringbootsListAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootsListAdminServerApplication.class, args);
	}
	@RequestMapping("index")
	public String index() {
		return "<h1>欢迎入AdminServer........</h1>";
	}
}
