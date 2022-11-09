package com.fdmgroup.QuizSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")  //http://localhost:8088/QuestionSystem/
	public String getHomePage() {
		return "Hello!!";
	} 
	
}
