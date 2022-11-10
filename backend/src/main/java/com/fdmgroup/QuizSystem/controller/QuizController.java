package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.dto.QuizInput;
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
@RequestMapping("/quizzes")  //http://localhost:8088/QuizSystem/quizzes
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	@GetMapping
	public String getQuizes() {
		return "Get quiz was called";
	}
	
	@PostMapping("/{user_id}")
	public String createQuiz(@RequestBody QuizInput quizInput) {

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
