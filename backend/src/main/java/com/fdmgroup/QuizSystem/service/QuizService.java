package com.fdmgroup.QuizSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {
	
	//@Autowired is not needed anymore as we already use @RequiredArgsConstructor for final fields
    private final QuizRepository quizRepository;


//    public Quiz getQuizById(long id){
//
//        Optional<Quiz> maybeQuiz = quizRepository.findById(id);
//        if(maybeQuiz.isEmpty()){
//            throw new QuizNotFoundException();
//        }
//        return maybeQuiz.get();
//    }
//
//
//    public Quiz getQuizByQuizname(String quizname){
//        Optional<Quiz> maybe_quiz = quizRepository.findQuizByQuizname(quizname);
//        if(maybe_quiz.isEmpty()){
//            throw new QuizNotFoundException();
//        }
//        return maybe_quiz.get();
//    }
//
//    
//    public void deleteQuizById(long id){
//        if(quizRepository.existsById(id)){
//            quizRepository.deleteById(id);
//        }
//        else {
//            throw new QuizNotFoundException();
//        }
//
//    }
//
//    
//    public Quiz updateQuiz(long id, Quiz modifiedQuiz) {
//        Optional<Quiz> maybeQuiz = quizRepository.findById(id);
//        if(maybeQuiz.isEmpty()){
//            throw new QuizNotFoundException();
//        }
//        // Update quiz with new attributes
//        Quiz quiz = maybeQuiz.get();
////        quiz.setAvatar(input.getAvatar());
//        quiz.setQuizname(modifiedQuiz.getQuizname());
//        quiz.setPassword(modifiedQuiz.getPassword());
//        quiz.setEmail(modifiedQuiz.getEmail());
//        return quizRepository.save(quiz);
//    }
//
//    
//    public boolean existsByQuizname(String quizname){
//        return quizRepository.existsByQuizname(quizname);
//    }
//
//    
//    public boolean existsByEmail(String email){
//        return quizRepository.existsByEmail(email);
//    }
//
//   
//    public Quiz save(Quiz quiz) {
//        return quizRepository.save(quiz);
//    }
}
