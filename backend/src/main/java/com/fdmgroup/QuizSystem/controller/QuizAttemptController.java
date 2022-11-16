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
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.QuizAttemptService;
import com.fdmgroup.QuizSystem.service.QuizQuestionMCQAttemptService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;

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
	@PostMapping("/{active_user_id}")
	public ResponseEntity<QuizAttemptDTO> createQuizAttempt(@PathVariable long active_user_id,@RequestBody QuizAttemptDTO quizAttemptDTO) {
		
		// Added by Summer: check if the active user has authority to take a certain quiz category
		QuizCategory requestedQuizCategory = quizService.getQuizById(quizAttemptDTO.getQuizId()).getQuizCategory();
		quizAttemptService.checkAccessToQuizCategory(requestedQuizCategory, active_user_id);
	
		
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
		quizAttemptDTOResponse.setMaxGrade(quizService.getMaxGrade(quizAttemptDTO.getQuizId()));
		quizAttempt = quizAttemptService.save(quizAttempt);
		
		return new ResponseEntity<>(quizAttemptDTOResponse,HttpStatus.CREATED);
	}
	

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
				quizAttemptDTO.setMCQAttemptList(quizAttemptService.getMCQAttemptsforOneQuizAttempt(quizAttempt));
				resultList.add(quizAttemptDTO);
			}
		}
		
		return new ResponseEntity<>(resultList,HttpStatus.OK );
	
	}


	
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
