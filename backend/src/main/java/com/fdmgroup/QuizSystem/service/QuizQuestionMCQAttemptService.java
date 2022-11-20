package com.fdmgroup.QuizSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;

import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttemptKey;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;

import lombok.RequiredArgsConstructor;



/**
 * Service class for MCQ attempts. Handles logic between the gear repositories and gear
 * controller.
 * @author Yutta
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class QuizQuestionMCQAttemptService {

    private final QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;

	private final QuizAttemptService quizAttemptService;
	private final QuestionService questionService;
	private final QuizQuestionGradeService qqgService;
	private final MultipleChoiceOptionService mcoService;

    /**
     * Persists a QuizQuestionMCQAttempt to the database
     * @param attempt
     * @return
     */
    public QuizQuestionMCQAttempt save(QuizQuestionMCQAttempt attempt) {
       return quizQuestionMCQAttemptRepository.save(attempt);
    }

    /**
     * Creates a QuizQuestionMCQAttempt given a MCQAttemptDTO
     * @param mcqAttemptDTO
     * @param quizAttemptId
     * @param quizId
     * @return managed instance of created entity
     */
    public QuizQuestionMCQAttempt createMCQAttempt(MCQAttemptDTO mcqAttemptDTO, long quizAttemptId, long quizId) {
		QuizQuestionMCQAttempt mcqAttempt = new QuizQuestionMCQAttempt();
	    QuizQuestionMCQAttemptKey key= new QuizQuestionMCQAttemptKey(quizAttemptId, mcqAttemptDTO.getMcqId());
		mcqAttempt.setKey(key);
		mcqAttempt.setQuizAttempt(quizAttemptService.findQuizAttemptById(quizAttemptId));
		mcqAttempt.setMultipleChoiceQuestion(questionService.findMcqById(mcqAttemptDTO.getMcqId()));
		mcqAttempt.setSelectedOption(mcoService.getMcqOptionById(mcqAttemptDTO.getSelectedOption()));
		if(mcqAttempt.getSelectedOption().isCorrect()) {
			QuizQuestionGradeKey qqgKey = new QuizQuestionGradeKey(quizId, mcqAttempt.getMultipleChoiceQuestion().getId());
			mcqAttempt.setAwarded_grade(qqgService.findById(qqgKey).getGrade());
		} else {
			mcqAttempt.setAwarded_grade(0);
		}
		return save(mcqAttempt);
	}
    
    /**
     * Get MCQ attempts given a quiz attempt id
     * @param quizAttemptId
     * @return MCQ attempts
     */
    public List<QuizQuestionMCQAttempt> findMcqAttemptsByAttemptId(long quizAttemptId){
    	return quizQuestionMCQAttemptRepository.findByQuizAttemptId(quizAttemptId);
    }
    
    /**
     * Get MCQ attempts given a quiz attempt object
     * @param qa
     * @return MCQ attempts
     */
    public List<QuizQuestionMCQAttempt> getMCQAttemptsByQuizAttempt(QuizAttempt qa){
    	return quizQuestionMCQAttemptRepository.findByQuizAttempt(qa);
    }

}
