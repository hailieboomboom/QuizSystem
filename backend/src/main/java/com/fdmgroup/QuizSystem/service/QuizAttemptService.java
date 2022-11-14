package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizAttemptService {
	
	@Autowired
	private final QuizAttemptRepository quizAttemptRepository;
	@Autowired
	private QuizQuestionMCQAttemptRepository mcqAttemptRepo;
	
	public QuizAttempt save (QuizAttempt quizAttempt) {
		return quizAttemptRepository.save(quizAttempt);
	}
	
	public QuizAttempt findQuizAttemptById(long id) {
		return quizAttemptRepository.findById(id).get(); //TODO exception??
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
}
