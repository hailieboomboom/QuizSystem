package com.fdmgroup.QuizSystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuizController {

	private static final String SUCCESS_PRODUCT_HAS_BEEN_DELETED = "Product has been deleted";
	public static final String SUCCESS_PRODUCT_HAS_BEEN_UPDATED = "Product has been updated";
	public static final String ERROR_USER_DOES_NOT_EXIST = "User does not exist";
	public static final String SUCCESS_QUIZ_HAS_BEEN_CREATED = "Quiz has been created";

	private final ModelToDTO modelToDTO;
	private final QuizService quizService;

	private final QuestionService questionService;

	private final QuizQuestionGradeService quizQuestionGradeService;
	private final UserService userService;


	@PostMapping("/api/quizzes/{active_user_id}")
	public ResponseEntity<QuizDto> createQuiz(@PathVariable long active_user_id, @RequestBody QuizDto quizDto) {

		// Sales and Training students can only create one type of quizzes
		quizService.checkAccessToQuizCategory(quizDto.getQuizCategory(), active_user_id);
		
		// Others(Trainers, Pond/Beached Students) can create both quizzes 
		QuizDto quizDtoResponse =  quizService.createQuiz(quizDto);
		return new ResponseEntity<QuizDto>(quizDtoResponse, HttpStatus.CREATED);
	}

	@GetMapping("/api/quizzes")
	public ResponseEntity<List<QuizDto>> getAllQuizzes() {
		List<QuizDto> quizDtos = quizService.getAllQuizzes();
		return new ResponseEntity<>(quizDtos, HttpStatus.OK);
	}


	@GetMapping("/api/quizzes/{id}")
	public ResponseEntity<QuizDto> getQuizById(@PathVariable("id") long id) {

		Quiz quiz = quizService.getQuizById(id);

		return new ResponseEntity<>(modelToDTO.quizToOutput(quiz), HttpStatus.OK);
	}

	@PutMapping("/api/quizzes/{id}/{active_user_id}")
	public ResponseEntity<ApiResponse> updateQuiz(@PathVariable long id, @PathVariable long active_user_id, @RequestBody QuizDto quizDto) {
		
		quizService.checkAccessToQuizId(id, active_user_id);	
		quizService.checkAccessToQuizCategory(quizDto.getQuizCategory(), active_user_id);
		
		quizService.updateQuiz(id, quizDto);
		return new ResponseEntity<>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_UPDATED), HttpStatus.OK);
	}

	
	@DeleteMapping("/api/quizzes/{id}/{active_user_id}")
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable long id, @PathVariable long active_user_id) {
		
		QuizCategory quizCategory = quizService.getQuizById(id).getQuizCategory();
		quizService.checkAccessToQuizId(id, active_user_id);	
		quizService.checkAccessToQuizCategory(quizCategory, active_user_id);
		
		quizService.deleteQuizById(id);
		return new ResponseEntity<>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_DELETED), HttpStatus.OK);
	}

	
//	@PostMapping("/api/quizzes/{id}/questions")
//	public ResponseEntity<ApiResponse> createQuizQuestions(@PathVariable("id") long quiz_id, @RequestBody List<QuestionGradeDTO> questionGradeDtoList) {
//        Quiz quiz = quizService.getQuizById(quiz_id);
//        for(QuestionGradeDTO questionGradeDTO : questionGradeDtoList ) {
//            Question question = questionService.findById(questionGradeDTO.getQuestionId());
//            float grade = questionGradeDTO.getGrade();
//            quizService.addQuestionIntoQuiz(question, quiz, grade);
//        }
//        return new ResponseEntity<>(new ApiResponse(true, "Successfully update questions to quiz"), HttpStatus.OK);
//    }


	
	@PostMapping("/api/quizzes/{id}/questions/{active_user_id}")
	public ResponseEntity<ApiResponse> createQuizQuestions(@PathVariable("id") long quiz_id, @PathVariable long active_user_id,  @RequestBody List<QuestionGradeDTO> questionGradeDtoList) {
		
		QuizCategory quizCategory = quizService.getQuizById(quiz_id).getQuizCategory();
		quizService.checkAccessToQuizId(quiz_id, active_user_id);	
		quizService.checkAccessToQuizCategory(quizCategory, active_user_id);

		quizService.createQuizQuestions(quiz_id, questionGradeDtoList);
		return new ResponseEntity<>(new ApiResponse(true, "Successfully update questions to quiz"), HttpStatus.OK);
	}

	
	@PutMapping("/api/quizzes/{id}/questions/{active_user_id}")
	public ResponseEntity<ApiResponse> updateQuizQuestions(@PathVariable("id") long quiz_id, @PathVariable long active_user_id, @RequestBody List<QuestionGradeDTO> questionGradeDtoList) {

		QuizCategory quizCategory = quizService.getQuizById(quiz_id).getQuizCategory();
		quizService.checkAccessToQuizId(quiz_id, active_user_id);	
		quizService.checkAccessToQuizCategory(quizCategory, active_user_id);

		quizService.updateQuizQuestions(quiz_id,questionGradeDtoList);
		return new ResponseEntity<>(new ApiResponse(true, "Successfully update questions to quiz"), HttpStatus.OK);
	}

	@GetMapping("/api/quizzes/{id}/questions")
	public ResponseEntity<List<QuestionGradeDTO>> getAllQuestionsByQuizId(@PathVariable long id) {
		
		List<QuestionGradeDTO> resultList = quizQuestionGradeService.findAllByQuizId(id).stream().map(modelToDTO::qqgToQg).toList();
		for(QuestionGradeDTO questionGradeDTO : resultList) {
			Question question = questionService.findById(questionGradeDTO.getQuestionId());
			questionGradeDTO.setTags(question.getTags());
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}
	
	
	// View quizzes created by a user
	@GetMapping("/api/quizzes/users/{id}")
	public ResponseEntity<List<QuizDto>> getQuizzesByCreatorId(@PathVariable("id") long creatorId){

			List<QuizDto> quizDtos = quizService.getQuizzesByCreatorId(creatorId);
			return new ResponseEntity<>(quizDtos, HttpStatus.OK);
		
	}

}
