package com.fdmgroup.QuizSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.service.QuizService;

@RestController
@RequestMapping("/api/quizzes")  //http://localhost:8088/QuizSystem/api/quizzes
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	@GetMapping
	public String getQuizes() {
		return "Get quiz was called";
	}
	
	@PostMapping
	public String createQuiz(@RequestBody Quiz quiz) {
		return "Created quiz was called";
	}
	
	@PutMapping
	public String updateQuiz() {
		return "Update quiz was called";
	}

	@DeleteMapping
	public String deleteQuiz() {
		return "Delete quiz was called";
	}
}
