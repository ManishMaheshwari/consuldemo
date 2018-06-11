package com.manish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConsuldemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsuldemoApplication.class, args);
	}

	@RequestMapping("/")
	public String root() {
		return "Hello Docker World";
	}

	@RequestMapping(method = {RequestMethod.GET}, value = {"/home"})
	public String home() {
		return "Hello from Server.";
	}
}
