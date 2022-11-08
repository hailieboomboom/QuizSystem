package com.fdmgroup.QuizSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz")
public class QuizController {
	
	@GetMapping
	public String getQuizes() {
		return "Got all the quizes";
	}
	
	@PostMapping
	public String createQuiz() {
		return "Created a quiz";
	}

}
