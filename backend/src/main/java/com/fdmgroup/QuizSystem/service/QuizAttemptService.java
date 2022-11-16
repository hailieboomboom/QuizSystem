package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.Quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizAttemptService {
	
	private final QuizAttemptRepository quizAttemptRepository;
	private final QuizQuestionMCQAttemptRepository mcqAttemptRepo;
	private final UserService userService;
	
	public QuizAttempt save (QuizAttempt quizAttempt) {
		return quizAttemptRepository.save(quizAttempt);
	}
	
	public QuizAttempt findQuizAttemptById(long id) {
		return quizAttemptRepository.findById(id).get(); //TODO exception??
	}

	public int calculateNumberOfAttempts(long quizAttemptid) {
		QuizAttempt quizAttempt = findQuizAttemptById(quizAttemptid);
		List<QuizAttempt> quizAttempts = quizAttemptRepository.findByQuizIdAndUserId(quizAttempt.getQuiz().getId(), quizAttempt.getUser().getId());
		return quizAttempts.size();
	}
	
	public float calculateTotalAwarded(Long attemptId){
        float total = 0;
        var attemptList = mcqAttemptRepo.findByQuizAttemptId(attemptId);
        for(QuizQuestionMCQAttempt attempt: attemptList)
            total+=attempt.getAwarded_grade();
        return total;
    }

	public List<QuizAttempt> findQuizAttemptByQuizId(long quizId) {
	
		return quizAttemptRepository.findByQuizId(quizId);
	}

	
	public void deleteAttempt(QuizAttempt quizAttempt) {
		// find associated MCQ Attempts
		List<QuizQuestionMCQAttempt> mcqAttempts = mcqAttemptRepo.findByQuizAttempt(quizAttempt);
		
		// delete quizQuestionattempt
		for(QuizQuestionMCQAttempt mcqAttempt: mcqAttempts) {
			mcqAttemptRepo.delete(mcqAttempt);
		}
		
		// delete quizAttempt itself
		quizAttemptRepository.delete(quizAttempt);
	}

	public List<QuizAttempt> findQuizAttemptByUser(User user) {
		
		return quizAttemptRepository.findByUser(user);
	}
	
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

	// Method added by Summer
	// check if the active user has the authority to take the quiz
	public void checkAccessToQuizCategory(QuizCategory requestQuizCategory, long activeUserId) {
		
		HashMap<QuizCategory, Set<Role>> quizRoleMap = new HashMap<>();
		quizRoleMap.put(QuizCategory.COURSE_QUIZ, new HashSet<Role>(Arrays.asList(
				Role.POND,Role.BEACHED,
				Role.TRAINING)));
		quizRoleMap.put(QuizCategory.INTERVIEW_QUIZ, new HashSet<Role>(Arrays.asList(
				Role.POND,Role.BEACHED)));
		
		Role activeUserRole = userService.getUserById(activeUserId).getRole();
		Set<Role> authorisedRoleSet = quizRoleMap.get(requestQuizCategory);
		
		if(!authorisedRoleSet.contains(activeUserRole)) {
			throw new UserUnauthorisedError("You do not have access to taking " + requestQuizCategory + " quizzes!");
		}
	}
	
}
