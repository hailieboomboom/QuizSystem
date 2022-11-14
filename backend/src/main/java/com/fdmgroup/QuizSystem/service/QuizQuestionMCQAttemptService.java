package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
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

@Service
@Transactional
@RequiredArgsConstructor
public class QuizQuestionMCQAttemptService {

    private final QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;

	private QuizQuestionMCQAttemptService mcqAttemptService;
	private QuizAttemptService quizAttemptService;
	private QuestionService questionService;
	private QuizQuestionGradeService qqgService;
	private MultipleChoiceOptionService mcoService;
	
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
		return mcqAttemptService.save(mcqAttempt);
	}
//    public QuizQuestionMCQAttempt findById(long quizId, long question_id, long user_id){
//        Quiz quiz = quizService.getQuizById(quizId);
//
//
//
//    }

    public List<MCQAttemptDTO> getMCQAttemptsforOneQuizAttempt(QuizAttempt qa){
        List<MCQAttemptDTO> mcqAttemptDtos = new ArrayList<MCQAttemptDTO>();
        List<QuizQuestionMCQAttempt> mcqAttempts = mcqAttemptService.getMCQAttemptsByQuizAttempt(qa);
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
