package com.fdmgroup.QuizSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizAttemptService {
	private final QuizAttemptRepository quizAttemptRepository;
	
	public QuizAttempt save (QuizAttempt quizAttempt) {
		return quizAttemptRepository.save(quizAttempt);
	}
	
	public QuizAttempt findQuizAttemptById(long id) {
		return quizAttemptRepository.findById(id).get(); //TODO exception??
	}

	public List<QuizAttempt> findQuizAttemptByQuizId(long quizId) {
	
		return quizAttemptRepository.findByQuizId(quizId);
	}
	
	public int calculateNumberOfAttempts(long id) {
        QuizAttempt quizAttempt = findQuizAttemptById(id);
        List<QuizAttempt> quizAttempts = quizAttemptRepository.findByQuizIdAndUserId(quizAttempt.getQuiz().getId(), quizAttempt.getUser().getId());
        return quizAttempts.size();
    }
}
