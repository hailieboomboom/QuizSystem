package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.util.Optional;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/quizzes")  //http://localhost:8088/QuizSystem/api/quizzes
public class QuizController {

	public static final String ERROR_USER_DOES_NOT_EXIST = "User does not exist";
	public static final String SUCCESS_QUIZ_HAS_BEEN_CREATED = "Quiz has been created";
	private QuizService quizService;
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<QuizDto>> getQuizzes() {
		quizService.getAllQuizzes();
		return new ResponseEntity<>(quizzes, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse> createQuiz(@RequestBody QuizDto quizDto) {
		Optional<User> optionalUser = userService.getUserById(quizDto.getCreatorId());
		// check if user exists
		if(optionalUser.isEmpty()){
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_USER_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
		}

		// create quiz in database
		quizService.createQuiz(quizDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESS_QUIZ_HAS_BEEN_CREATED), HttpStatus.CREATED);
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
