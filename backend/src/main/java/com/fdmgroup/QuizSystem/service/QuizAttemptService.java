package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.exception.QuizAttemptNotFoundException;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;

import lombok.RequiredArgsConstructor;


/**
 * Service class for quiz attempts. Handles logic between the gear repositories and gear
 * controller.
 * @author Yutta
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class QuizAttemptService {
	
	private final QuizAttemptRepository quizAttemptRepository;
	private final QuizQuestionMCQAttemptRepository mcqAttemptRepo;
	private final UserService userService;
	
	/**
	 * Persists a QuizAttempt to the database
	 * @param quizAttempt
	 * @return a managed QuizAttempt
	 */
	public QuizAttempt save (QuizAttempt quizAttempt) {
		return quizAttemptRepository.save(quizAttempt);
	}
	
	/**
	 * Get QuizAttempt by its id
	 * @param id QuizAttempt id
	 * @return a managed QuizAttempt
	 */
	public QuizAttempt findQuizAttemptById(long id) {
		Optional<QuizAttempt> maybeQuizAttempt = quizAttemptRepository.findById(id);
		if(maybeQuizAttempt.isEmpty()) {
			throw new QuizAttemptNotFoundException();
		}
		return maybeQuizAttempt.get();
	}

	/**
	 * Calculates the number of attempts a user made on a quiz
	 * @param quizAttemptid
	 * @return number of attempts a user made on a quiz
	 */
	public int calculateNumberOfAttempts(long quizAttemptid) {
		QuizAttempt quizAttempt = findQuizAttemptById(quizAttemptid);
		List<QuizAttempt> quizAttempts = quizAttemptRepository.findByQuizIdAndUserId(quizAttempt.getQuiz().getId(), quizAttempt.getUser().getId());
		return quizAttempts.size();
	}
	
	/**
	 * Calculates the total grade awarded for all questions in the quiz
	 * @param attemptId QuizAttempt id
	 * @return the total grade awarded
	 */
	public float calculateTotalAwarded(Long attemptId){
        float total = 0;
        var attemptList = mcqAttemptRepo.findByQuizAttemptId(attemptId);
        for(QuizQuestionMCQAttempt attempt: attemptList)
            total+=attempt.getAwarded_grade();
        return total;
    }
	
	/**
	 * Gets all Quiz attempt for a quiz
	 * @param quizId Quiz id
	 * @return list of all Quiz attempt for a quiz
	 */
	public List<QuizAttempt> findQuizAttemptByQuizId(long quizId) {
	
		return quizAttemptRepository.findByQuizId(quizId);
	}

	/**
	 * delete a quiz attempt and associated entities
	 * @param quizAttempt
	 */
	public void deleteAttempt(QuizAttempt quizAttempt) {
	
		var attempt= mcqAttemptRepo.findByQuizAttempt(quizAttempt);
		if(!attempt.isEmpty() && attempt!=null ){
			List<QuizQuestionMCQAttempt> mcqAttempts = mcqAttemptRepo.findByQuizAttempt(quizAttempt);
			
			for(QuizQuestionMCQAttempt mcqAttempt: mcqAttempts) {
				mcqAttemptRepo.delete(mcqAttempt);
			}
		}
		quizAttemptRepository.delete(quizAttempt);
	}

	/**
	 * Get all quiz attempts taken by a user
	 * @param user
	 * @return all quiz attempts taken by a user
	 */
	public List<QuizAttempt> findQuizAttemptByUser(User user) {
		
		return quizAttemptRepository.findByUser(user);
	}
	
	/**
	 * Get MCQ attempts as DTO for a quiz attempts
	 * @param qa
	 * @return list of MCQ attempts as DTO for a quiz attempts
	 */
	public List<MCQAttemptDTO> getMCQAttemptsforOneQuizAttempt(QuizAttempt qa){
		List<MCQAttemptDTO> mcqAttemptDtos = new ArrayList<MCQAttemptDTO>();
		List<QuizQuestionMCQAttempt> mcqAttempts = mcqAttemptRepo.findByQuizAttempt(qa);
		for(QuizQuestionMCQAttempt mcqa : mcqAttempts) {
			MCQAttemptDTO mcqaDto = new MCQAttemptDTO();
			mcqaDto.setMcqId(mcqa.getMultipleChoiceQuestion().getId());
			mcqaDto.setAwarded_grade(mcqa.getAwarded_grade());
			mcqaDto.setSelectedOption(mcqa.getSelectedOption().getId());
			mcqaDto.setQuizAttemptId(mcqa.getQuizAttempt().getId());
			mcqAttemptDtos.add(mcqaDto);
		}
		
		return mcqAttemptDtos;
	}
	
	/**
	 * Check if the active user has the authority to take the quiz
	 * @param requestQuizCategory
	 * @param activeUserId
	 */
	public void checkAccessToQuizCategory(QuizCategory requestQuizCategory, long activeUserId) {
		
		HashMap<QuizCategory, Set<Role>> quizRoleMap = new HashMap<>();
		quizRoleMap.put(QuizCategory.COURSE_QUIZ, new HashSet<Role>(Arrays.asList(
				Role.AUTHORISED_TRAINER,
				Role.POND,Role.BEACHED,
				Role.TRAINING)));
		quizRoleMap.put(QuizCategory.INTERVIEW_QUIZ, new HashSet<Role>(Arrays.asList(
				Role.AUTHORISED_TRAINER,
				Role.POND,Role.BEACHED,
				Role.AUTHORISED_SALES)));
		
		Role activeUserRole = userService.getUserById(activeUserId).getRole();
		Set<Role> authorisedRoleSet = quizRoleMap.get(requestQuizCategory);
		
		if(!authorisedRoleSet.contains(activeUserRole)) {
			throw new UserUnauthorisedError("You do not have access to taking " + requestQuizCategory + " quizzes!");
		}
	}
	
}
