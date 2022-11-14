package com.fdmgroup.QuizSystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.dto.Attempt.QuizAttemptDTO;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.service.QuizAttemptService;
import com.fdmgroup.QuizSystem.service.QuizQuestionMCQAttemptService;
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
		
		// creat method in quizattemptService to calculate totalawrded - CHRIS -> Service
		// calculate attempt number
		
		//TODO map to dto (ask jason)
		QuizAttemptDTO quizAttemptGradedDTO = new QuizAttemptDTO();
		return new ResponseEntity<>(quizAttemptGradedDTO,HttpStatus.CREATED);
	}
	
	
	// view attempt by attempt ID
//	@GetMapping("/{attempt_id}")

	// view attempts of quizzes created by a user
	@GetMapping("/quizCreator/{creator_id}")
	public ResponseEntity<List<QuizAttemptDTO>> getQuizAttemptsByQuizCreatorId(@PathVariable("creator_id") long quizCreatorId){
		
		
		List<QuizDto> quizDtoList = quizService.getQuizzesByCreatorId(quizCreatorId);
		Set<Long> quizIdSet = quizDtoList.stream().map(quizDto -> quizDto.getQuizId()).collect(Collectors.toSet());
		
		List<QuizAttemptDTO> resultList = new ArrayList<>();
		for(Long quizId: quizIdSet) {
			List<QuizAttempt> quizAttemptList = quizAttemptService.findQuizAttemptByQuizId(quizId);
			
			for(QuizAttempt quizAttempt: quizAttemptList) {
				QuizAttemptDTO quizAttemptDTO = new QuizAttemptDTO();
				quizAttemptDTO.setId(quizAttempt.getId());
				quizAttemptDTO.setQuizId(quizAttempt.getQuiz().getId());
				quizAttemptDTO.setUserId(quizAttempt.getUser().getId());
				quizAttemptDTO.setAttemptNo(quizAttemptService.calculateNumberOfAttempts(quizAttempt.getId()));
				quizAttemptDTO.setTotalAwarded(quizAttempt.getTotalAwarded());
				// TODO: for Summer to confirm that there is no need to show List<MCQAttemptDTO> MCQAttemptList here ?
				resultList.add(quizAttemptDTO);
			}
		}
		
		return new ResponseEntity<>(resultList,HttpStatus.OK );
	
	}

//	@GetMapping("/quizTaker/{attempted_by_id}")
	// view all attempts by user(student) (user, quiz_name/attempt, quiz_grade)
	
	
	// delete an attempt (associated with a quiz to be deleted) -> move to quizAttemptService

}
