package com.fdmgroup.QuizSystem.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions") // http://localhost:8088/QuestionSystem/questions
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@PostMapping("/saq")
	public ResponseEntity<ApiResponse> postShortAnswerQuestion(@RequestBody ShortAnswerQuestion saq ) throws URISyntaxException {
		if(saq.getQuestionDetails().isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "empty question body"),HttpStatus.BAD_REQUEST);
		
		}
		
		// if question answer is not provided
		if(saq.getCorrectAnswer().isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "empty question answer"),HttpStatus.BAD_REQUEST);
			
		}
		
		// if both question detail and correct answer are filled
		// save the created entity
		Question saqResult = questionService.save(saq);
		return new ResponseEntity<>(new ApiResponse(true, "create short answer question success"), HttpStatus.OK);
	}

//	@GetMapping
//	public String getQuestions() {
//		return "Got all the questiones";
//	}
//	
//	@PostMapping
//	public String createQuestion(@RequestBody Question question) {
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
