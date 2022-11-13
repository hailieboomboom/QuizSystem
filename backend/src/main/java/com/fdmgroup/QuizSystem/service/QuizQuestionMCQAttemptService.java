package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizQuestionMCQAttemptService {

    private final QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;

    private final QuizService quizService;

    public QuizQuestionMCQAttempt save(QuizQuestionMCQAttempt attempt) {
       return quizQuestionMCQAttemptRepository.save(attempt);
    }

//    public QuizQuestionMCQAttempt findById(long quizId, long question_id, long user_id){
//        Quiz quiz = quizService.getQuizById(quizId);
//
//
//
//    }


}
