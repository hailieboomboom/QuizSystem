package com.fdmgroup.QuizSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
