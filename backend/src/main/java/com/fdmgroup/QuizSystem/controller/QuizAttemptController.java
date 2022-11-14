package com.fdmgroup.QuizSystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.dto.Attempt.QuizAttemptDTO;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttemptKey;
import com.fdmgroup.QuizSystem.repository.MultipleChoiceOptionRepository;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.service.MultipleChoiceOptionService;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizAttemptService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizQuestionMCQAttemptService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/quizAttempts") // http://localhost:8088/QuestionSystem/questions
public class QuizAttemptController {

	private QuizQuestionMCQAttemptService mcqAttemptService;
	private QuizAttemptService quizAttemptService;
	private QuizService quizService;
	private UserService userService;
	private ModelToDTO modelToDTO;

	// create an quiz attempt (return graded attempt)
	@PostMapping
	public ResponseEntity<QuizAttemptDTO> createQuizAttempt(@RequestBody QuizAttemptDTO quizAttemptDTO) {
		QuizAttempt quizAttempt = new QuizAttempt();
		quizAttempt.setQuiz(quizService.getQuizById(quizAttemptDTO.getQuizId()));
		quizAttempt.setUser(userService.getUserById(quizAttemptDTO.getUserId()));
		quizAttempt = quizAttemptService.save(quizAttempt);
		
		for(MCQAttemptDTO mcqAttemptDTO: quizAttemptDTO.getMCQAttemptList()) {
			mcqAttemptService.createMCQAttempt(mcqAttemptDTO, quizAttempt.getId(), quizAttempt.getQuiz().getId());
		}
		
		// creat method in quizattemptService to calculate totalawrded
//		quizAttemptService.calculateTotalAwarded(quizAttempt);//set total awarded
		// calculate attempt number

		quizAttempt.setAttemptNo(quizAttemptService.calculateNumberOfAttempts(quizAttempt.getId()));
		quizAttempt.setTotalAwarded(quizAttemptService.calculateTotalAwarded(quizAttempt.getId()));
		//TODO map to dto (ask jason)
		QuizAttemptDTO quizAttemptDTOResponse = modelToDTO.quizAttemptToOutput(quizAttempt);
		List<MCQAttemptDTO> mcqResponses = new ArrayList<>();
		for (QuizQuestionMCQAttempt mcqAttempt : mcqAttemptService.findMcqAttemptsByAttemptId(quizAttempt.getId())) {
			mcqResponses.add(modelToDTO.mcqAttemptToOutput(mcqAttempt));
		}
		
		quizAttemptDTOResponse.setMCQAttemptList(mcqResponses);
		quizAttempt = quizAttemptService.save(quizAttempt);
		
		return new ResponseEntity<>(quizAttemptDTOResponse,HttpStatus.CREATED);
		
	}

//	@GetMapping("/{created_by_id}")
	// view attempts of quizzes created 

//	@GetMapping("/{attempted_by_id}")
	// view all attempts by user(student) (user, quiz name/attempt, quiz_grade)
	
	// delete an attempt (associated with a quiz to be deleted) -> move to quizAttemptService

}
