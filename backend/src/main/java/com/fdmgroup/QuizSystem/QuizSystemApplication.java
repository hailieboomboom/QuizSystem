package com.fdmgroup.QuizSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class QuizSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizSystemApplication.class, args);
		System.out.println("-------------------- http://localhost:8088/QuizSystem  --------------");
	}

}
