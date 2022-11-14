package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttemptKey;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizQuestionMCQAttemptService {

    private final QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;

	private final QuizAttemptService quizAttemptService;
	private final QuestionService questionService;
	private final QuizQuestionGradeService qqgService;
	private final MultipleChoiceOptionService mcoService;
	
    private final QuizService quizService;

    public QuizQuestionMCQAttempt save(QuizQuestionMCQAttempt attempt) {
       return quizQuestionMCQAttemptRepository.save(attempt);
    }

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
    
    public List<QuizQuestionMCQAttempt> findMcqAttemptsByAttemptId(long quizAttemptId){
    	return quizQuestionMCQAttemptRepository.findByQuizAttemptId(quizAttemptId);
    }
    
//    public QuizQuestionMCQAttempt findById(long quizId, long question_id, long user_id){
//        Quiz quiz = quizService.getQuizById(quizId);
//
//
//
//    }


}
