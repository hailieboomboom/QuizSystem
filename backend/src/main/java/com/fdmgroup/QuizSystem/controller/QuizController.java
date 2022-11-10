package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.service.QuizService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/quizzes")  //http://localhost:8088/QuizSystem/api/quizzes
public class QuizController {
	
	private QuizService quizService;
	
	@GetMapping
	public String getQuizes() {
		return "Get quiz was called";
	}
	

	@PostMapping
	public Quiz createQuiz(@RequestBody Quiz quiz) {

		quizService.save(quiz);
		 
		return null;
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
