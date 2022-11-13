package com.fdmgroup.QuizSystem.controller;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.dto.QuizRequest;
import com.fdmgroup.QuizSystem.dto.QuizResponse;
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

	private final ModelToDTO modelToDTO;
	private final QuizService quizService;

	private UserRepository userRepository;

	private final QuestionService questionService;

	private QuizQuestionGradeService quizQuestionGradeService;

	@PostMapping("/api/quizzes")
	public ResponseEntity<ApiResponse> createQuiz(@RequestBody QuizRequest quizRequest) {

		quizService.createQuiz(quizRequest);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESS_QUIZ_HAS_BEEN_CREATED),
				HttpStatus.CREATED);
	}

//	@GetMapping("/api/quizzes")
//	public ResponseEntity<List<QuizRequest>> getAllQuizzes() {
//		List<QuizRequest> quizRequests = quizService.getAllQuizzes();
//		return new ResponseEntity<>(quizRequests, HttpStatus.OK);
//	}

	@GetMapping("/api/quizzes")
	public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
		List<QuizResponse> quizResponses = quizService.getAllQuizzes();
		return new ResponseEntity<>(quizResponses, HttpStatus.OK);
	}

	@GetMapping("/api/quizzes/{id}")
	public ResponseEntity<QuizResponse> getQuizById(@PathVariable("id") long id) {

		Quiz quiz = quizService.getQuizById(id);

		return new ResponseEntity<>(modelToDTO.quizToOutput(quiz), HttpStatus.OK);
	}

	@PutMapping("/api/quizzes/{id}")
	public ResponseEntity<ApiResponse> updateQuiz(@PathVariable long id, @RequestBody QuizRequest quizRequest) {

		quizService.updateQuiz(id, quizRequest);
		return new ResponseEntity<>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_UPDATED), HttpStatus.OK);
	}

//	@PutMapping("/api/quizzes/{id}/details")
//	@PutMapping("/api/quizzes/{id}/questions")

	// TODO to be completed
	@DeleteMapping("/api/quizzes/{id}")
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable long id) {
		quizService.deleteQuizById(id);
		return new ResponseEntity<>(new ApiResponse(true, SUCCESS_PRODUCT_HAS_BEEN_DELETED), HttpStatus.OK);
	}

//	@PostMapping("/api/quizzes/{quiz_id}/questions")
//	public ResponseEntity<ApiResponse> addQuestionToQuiz(@PathVariable long quiz_id, @RequestBody List<QuestionGradeDTO> questionGradeList) {
//		Quiz quiz = quizService.getQuizById(quiz_id);
//
//		for(QuestionGradeDTO questionGradeDTO : questionGradeList) {
//
//			Question question = questionService.findById(questionGradeDTO.getQuestionId());
//			float grade = questionGradeDTO.getGrade();
//			quizService.addQuestionIntoQuiz(question, quiz, grade);
//		}
//
//		return new ResponseEntity<>(new ApiResponse(true, "Successfully add questions to quizzes"), HttpStatus.OK);
//	}
	@PostMapping("/api/quizzes/{quiz_id}/questions")
	public ResponseEntity<ApiResponse> updateQuestionsToQuiz(@PathVariable long quiz_id, @RequestBody List<QuestionGradeDTO> questionGradeList) {

		Quiz quiz = quizService.getQuizById(quiz_id);

		List<QuizQuestionGrade> quizQuestionGradeList = quizQuestionGradeService.findAllByQuizId(quiz_id);
		// Database
		Set<Long> questionIdSet = quizQuestionGradeList.stream().map(quizQuestionGrade -> quizQuestionGrade.getQuestion().getId()).collect(Collectors.toSet());
		Set<Long> questionIdInputSet = questionGradeList.stream().map(QuestionGradeDTO::getQuestionId).collect(Collectors.toSet());

		for (QuestionGradeDTO questionGradeDTO : questionGradeList) {

			if(!questionIdSet.contains(questionGradeDTO.getQuestionId())) {
				Question question = questionService.findById(questionGradeDTO.getQuestionId());
				float grade = questionGradeDTO.getGrade();
				quizService.addQuestionIntoQuiz(question, quiz, grade);
			}
			else if(! questionGradeDTO.getGrade().equals(quizQuestionGradeService.findById( new QuizQuestionGradeKey(quiz_id, questionGradeDTO.getQuestionId())).getGrade())){
				QuizQuestionGrade quizQuestionGrade = quizQuestionGradeService.findById( new QuizQuestionGradeKey(quiz_id, questionGradeDTO.getQuestionId()));
				quizQuestionGrade.setGrade(questionGradeDTO.getGrade());
				quizQuestionGradeService.save(quizQuestionGrade);
			}
		}

		for(Long id : questionIdSet) {
			if(!questionIdInputSet.contains(id)) {
				Question question = questionService.findById(id);
				quizService.removeQuestionFromQuiz(question, quiz);
			}
		}

		return new ResponseEntity<>(new ApiResponse(true, "Successfully update questions to quiz"), HttpStatus.OK);
	}

	@GetMapping("/api/quizzes/{id}/questions")
	public ResponseEntity<List<QuestionGradeDTO>> getAllQuestionsByQuizId(@PathVariable long id) {
		List<QuestionGradeDTO> resultList = quizQuestionGradeService.findAllByQuizId(id).stream().map(modelToDTO::qqgToQg).toList();
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

}
