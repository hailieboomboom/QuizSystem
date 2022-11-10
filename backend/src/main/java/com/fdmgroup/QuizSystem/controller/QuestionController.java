package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/questions") // http://localhost:8088/QuestionSystem/questions
public class QuestionController {

	@Autowired
	private QuestionService questionService;

//	@GetMapping
//	public String getQuestions() {
//		return "Got all the questiones";
//	}
//	
//	@PostMapping("/{user_id}")
//	public String createQuestion(@RequestBody Question question) {
//
//		return "Created question was called";
//	}
//	
//	@PutMapping
//	public String updateQuestion() {
//		return "Update question was called";
//	}
//
//	@DeleteMapping
//	public String deleteQuestion() {
//		return "Delete question was called";
//	}
}
