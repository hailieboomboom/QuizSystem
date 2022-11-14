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
import com.fdmgroup.QuizSystem.model.User;
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

	// create an quiz attempt (return graded attempt)
	@PostMapping
	public ResponseEntity<QuizAttemptDTO> createQuizAttempt(@RequestBody QuizAttemptDTO quizAttemptDTO) {
		QuizAttempt quizAttempt = new QuizAttempt();
		quizAttempt.setQuiz(quizService.getQuizById(quizAttemptDTO.getId()));
		quizAttempt.setUser(userService.getUserById(quizAttemptDTO.getId()));
		quizAttempt = quizAttemptService.save(quizAttempt);
		
		for(MCQAttemptDTO mcqAttemptDTO: quizAttemptDTO.getMCQAttemptList()) {
			mcqAttemptService.createMCQAttempt(mcqAttemptDTO, quizAttempt.getId(), quizAttempt.getQuiz().getId());
			
		}
		// create method in quizattemptService to calculate total awarded
		
		// calculate attempt number
		
		
		//TODO map to dto (ask jason)
		QuizAttemptDTO quizAttemptGradedDTO = new QuizAttemptDTO();
		return new ResponseEntity<>(quizAttemptGradedDTO,HttpStatus.CREATED);
		
	}

//	@GetMapping("/{created_by_id}")
	// view attempts of quizzes created 

	
	// view all attempts by user(student) (user, quiz name/attempt, quiz_grade)
	@GetMapping("/quizTaker/{attempted_by_id}")
	public ResponseEntity<List<QuizAttemptDTO>> getAllQuizAttemptsByTaker(@PathVariable long attempted_by_id){
		List<QuizAttemptDTO> returnedAttemptDTOs = new ArrayList<QuizAttemptDTO>();
		User user = userService.getUserById(attempted_by_id);
		List<QuizAttempt> foundAttempts = quizAttemptService.findQuizAttemptByUser(user);
		
		for(QuizAttempt qa: foundAttempts) {
			QuizAttemptDTO qaDto = new QuizAttemptDTO();
			qaDto.setAttemptNo(qa.getAttemptNo());
			qaDto.setId(qa.getId());
			qaDto.setQuizId(qa.getQuiz().getId());
			qaDto.setTotalAwarded(qa.getTotalAwarded());
			qaDto.setUserId(qa.getUser().getId());
			qaDto.setMCQAttemptList(quizAttemptService.getMCQAttemptsforOneQuizAttempt(qa));
			returnedAttemptDTOs.add(qaDto);
		}
		
		return new ResponseEntity<>(returnedAttemptDTOs, HttpStatus.OK);
	}
	
}
