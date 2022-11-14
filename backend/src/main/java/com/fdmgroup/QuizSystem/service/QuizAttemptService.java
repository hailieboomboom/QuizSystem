package com.fdmgroup.QuizSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizAttemptService {
	private final QuizAttemptRepository quizAttemptRepository;
	private final QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;
	
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
        var attemptList = quizQuestionMCQAttemptRepository.findByQuizAttemptId(attemptId);
        for(QuizQuestionMCQAttempt attempt: attemptList)
            total+=attempt.getAwarded_grade();
        return total;
    }
	
	
}
