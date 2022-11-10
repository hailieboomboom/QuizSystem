package com.fdmgroup.QuizSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import com.fdmgroup.QuizSystem.service.QuizService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuizController {

	private static final String SUCCESS_PRODUCT_HAS_BEEN_DELETED = "Product has been deleted";
	public static final String SUCCESS_PRODUCT_HAS_BEEN_UPDATED = "Product has been updated";
	public static final String ERROR_USER_DOES_NOT_EXIST = "User does not exist";
	public static final String SUCCESS_QUIZ_HAS_BEEN_CREATED = "Quiz has been created";

	private QuizService quizService;
	private UserRepository userRepository;

	@PostMapping("/api/quizzes")
	public ResponseEntity<ApiResponse> createQuiz(@RequestBody QuizDto quizDto) {
		
//		// TODO do we need this? or do we need to check if the 
//		Optional<User> optionalUser = userRepository.findById(quizDto.getCreatorId());
//
//		// check if user exists
//		if (optionalUser.isEmpty()) {
//			return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_USER_DOES_NOT_EXIST),
//					HttpStatus.BAD_REQUEST);
//		}

		// create quiz in database
		quizService.createQuiz(quizDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESS_QUIZ_HAS_BEEN_CREATED),
				HttpStatus.CREATED);
	}
	
	@GetMapping("/api/quizzes")
	public ResponseEntity<List<QuizDto>> getAllQuizzes() {
		List<QuizDto> quizDtos = quizService.getAllQuizzes();
		return new ResponseEntity<>(quizDtos, HttpStatus.OK);
	}
	
    /**
     * Get quiz DTO by its id.
     * @param id Quiz id.
     * @return   Quiz DTO.
     */
	@GetMapping("/api/quizzes/{id}")
	public ResponseEntity<QuizDto> getQuizById(@PathVariable("id") long id){
		
		QuizDto quizDto = quizService.getQuizById(id);
		
		return new ResponseEntity<>(quizDto, HttpStatus.OK);
	}

//	@GetMapping("/api/content-quizzes")
//	public ResponseEntity<List<QuizDto>> getContentQuizzes(){
//
//		List<QuizDto> contentQuizDtos = quizService.getContentQuizzes();
//		
//		return new ResponseEntity<>(contentQuizDtos, HttpStatus.OK);
//	}

	@PutMapping("/api/quizzes/{id}")
	public ResponseEntity<ApiResponse> updateQuiz(@PathVariable long id, @RequestBody QuizDto quizDto) {

		quizService.updateQuiz(id, quizDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_UPDATED), HttpStatus.OK);
	}

	//TODO to be completed
	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable long id) {
		
		quizService.deleteQuizById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_DELETED), HttpStatus.OK);
	}
	
	

	

	
}
