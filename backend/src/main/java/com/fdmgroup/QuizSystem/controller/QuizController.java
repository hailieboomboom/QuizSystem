package com.fdmgroup.QuizSystem.controller;
import java.util.List;

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
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;

import lombok.AllArgsConstructor;

/**
 * Controller that handles all quiz requests
 * @author sm
 *
 */
@RestController
@AllArgsConstructor
public class QuizController {

	private static final String SUCCESS_PRODUCT_HAS_BEEN_DELETED = "Product has been deleted";
	public static final String SUCCESS_PRODUCT_HAS_BEEN_UPDATED = "Product has been updated";

	private final ModelToDTO modelToDTO;
	private final QuizService quizService;
	private final QuestionService questionService;
	private final QuizQuestionGradeService quizQuestionGradeService;


	/**
	 * Handles creating a quiz request.
	 * @param activeUserId the id of the logged-in user
	 * @param quizDto quiz DTO that contains in the request body
	 * @return  responseEntity which contains httpsStatus and quizDto
	 */
	@PostMapping("/api/quizzes/{activeUserId}")
	public ResponseEntity<QuizDto> createQuiz(@PathVariable long activeUserId, @RequestBody QuizDto quizDto) {

		// check if the user role has access to the requested quiz category
		quizService.checkAccessToQuizCategory(quizDto.getQuizCategory(), activeUserId);
		
		QuizDto quizDtoResponse =  quizService.createQuiz(quizDto);
		return new ResponseEntity<>(quizDtoResponse, HttpStatus.CREATED);
	}
	
	
	/**
	 * 	Handles getting all quizzes request.
	 * @return responseEntity which contains httpsStatus and quiz items
	 */
	@GetMapping("/api/quizzes")
	public ResponseEntity<List<QuizDto>> getAllQuizzes() {
		List<QuizDto> quizDtos = quizService.getAllQuizzes();
		return new ResponseEntity<>(quizDtos, HttpStatus.OK);
	}

	/**
	 * Handles getting a quiz by id request.
	 * @param id the id of the requested quiz
	 * @return responseEntity which contains httpsStatus and quiz item
	 */
	@GetMapping("/api/quizzes/{id}")
	public ResponseEntity<QuizDto> getQuizById(@PathVariable("id") long id) {

		Quiz quiz = quizService.getQuizById(id);

		return new ResponseEntity<>(modelToDTO.quizToOutput(quiz), HttpStatus.OK);
	}

	/**
	 * Handles updating quiz request.
	 * @param id the id of the quiz to be updated
	 * @param activeUserId the id of the logged-in user
	 * @param quizDto the quiz Dto contains updated quiz information
	 * @return responseEntity which contains httpsStatus of the request
	 */
	@PutMapping("/api/quizzes/{id}/{activeUserId}")
	public ResponseEntity<ApiResponse> updateQuiz(@PathVariable long id, @PathVariable long activeUserId, @RequestBody QuizDto quizDto) {
		
		quizService.checkAccessToQuizId(id, activeUserId);	
		quizService.checkAccessToQuizCategory(quizDto.getQuizCategory(), activeUserId);
		
		quizService.updateQuiz(id, quizDto);
		return new ResponseEntity<>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_UPDATED), HttpStatus.OK);
	}

	/**
	 * Handles deleting quiz request.
	 * @param id The id of the quiz to be deleted
	 * @param activeUserId The id of the logged-in user
	 * @return responseEntity which contains httpsStatus of the request
	 */
	@DeleteMapping("/api/quizzes/{id}/{activeUserId}")
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable long id, @PathVariable long activeUserId) {
		
		QuizCategory quizCategory = quizService.getQuizById(id).getQuizCategory();
		quizService.checkAccessToQuizId(id, activeUserId);	
		quizService.checkAccessToQuizCategory(quizCategory, activeUserId);
		
		quizService.deleteQuizById(id);
		return new ResponseEntity<>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_DELETED), HttpStatus.OK);
	}

	/**
	 * Handles the request for creating/adding the questions of the quiz.
	 * @param quiz_id The id of the quiz to which the questions are added 
	 * @param activeUserId The id of the logged-in user
	 * @param questionGradeDtoList  The list of questionGradeDto to be added 
	 * @return responseEntity which contains the result of process request and messages
	 */
	@PostMapping("/api/quizzes/{id}/questions/{activeUserId}")
	public ResponseEntity<ApiResponse> createQuizQuestions(@PathVariable("id") long quiz_id, @PathVariable long activeUserId,  @RequestBody List<QuestionGradeDTO> questionGradeDtoList) {
		
		QuizCategory quizCategory = quizService.getQuizById(quiz_id).getQuizCategory();
		quizService.checkAccessToQuizId(quiz_id, activeUserId);	
		quizService.checkAccessToQuizCategory(quizCategory, activeUserId);

		quizService.createQuizQuestions(quiz_id, questionGradeDtoList);
		return new ResponseEntity<>(new ApiResponse(true, "Successfully update questions to quiz"), HttpStatus.OK);
	}

	
	/**
	 * Handles requests for updating the questions of the quiz.
	 * @param quiz_id The id of the quiz to which the questions are updated 
	 * @param activeUserId The id of the logged-in user
	 * @param questionGradeDtoList  The list of questionGradeDto to be updated 
	 * @return responseEntity which contains the result of process request and messages
	 */
	@PutMapping("/api/quizzes/{id}/questions/{activeUserId}")
	public ResponseEntity<ApiResponse> updateQuizQuestions(@PathVariable("id") long quiz_id, @PathVariable long activeUserId, @RequestBody List<QuestionGradeDTO> questionGradeDtoList) {

		QuizCategory quizCategory = quizService.getQuizById(quiz_id).getQuizCategory();
		quizService.checkAccessToQuizId(quiz_id, activeUserId);	
		quizService.checkAccessToQuizCategory(quizCategory, activeUserId);

		quizService.updateQuizQuestions(quiz_id,questionGradeDtoList);
		return new ResponseEntity<>(new ApiResponse(true, "Successfully update questions to quiz"), HttpStatus.OK);
	}

	/**
	 * Handles requests for getting all questions by the quiz id.
	 * @param id The id of the requested quiz
	 * @return  responseEntity which contains the result of process request and messages
	 */
	@GetMapping("/api/quizzes/{id}/questions")
	public ResponseEntity<List<QuestionGradeDTO>> getAllQuestionsByQuizId(@PathVariable long id) {
		
		List<QuestionGradeDTO> resultList = quizQuestionGradeService.findAllByQuizId(id).stream().map(modelToDTO::qqgToQg).toList();
		for(QuestionGradeDTO questionGradeDTO : resultList) {
			Question question = questionService.findById(questionGradeDTO.getQuestionId());
			questionGradeDTO.setTags(question.getTags());
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}
	
	
	/**
	 * Handles requests for getting all quizzes that are created by the given user id
	 * @param creatorId The id of the creator of the quiz
	 * @return responseEntity which contains the result of process request and messages
	 */
	@GetMapping("/api/quizzes/users/{id}")
	public ResponseEntity<List<QuizDto>> getQuizzesByCreatorId(@PathVariable("id") long creatorId){

			List<QuizDto> quizDtos = quizService.getQuizzesByCreatorId(creatorId);
			return new ResponseEntity<>(quizDtos, HttpStatus.OK);
		
	}

}
