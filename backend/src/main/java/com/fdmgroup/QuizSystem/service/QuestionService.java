package com.fdmgroup.QuizSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;


//    public Question getQuestionById(long id){
//
//        Optional<Question> maybeQuestion = questionRepository.findById(id);
//        if(maybeQuestion.isEmpty()){
//            throw new QuestionNotFoundException();
//        }
//        return maybeQuestion.get();
//    }
//
//
//    public Question getQuestionByQuestionname(String questionname){
//        Optional<Question> maybe_question = questionRepository.findQuestionByQuestionname(questionname);
//        if(maybe_question.isEmpty()){
//            throw new QuestionNotFoundException();
//        }
//        return maybe_question.get();
//    }
//
//    
//    public void deleteQuestionById(long id){
//        if(questionRepository.existsById(id)){
//            questionRepository.deleteById(id);
//        }
//        else {
//            throw new QuestionNotFoundException();
//        }
//
//    }
//
//    
//    public Question updateQuestion(long id, Question modifiedQuestion) {
//        Optional<Question> maybeQuestion = questionRepository.findById(id);
//        if(maybeQuestion.isEmpty()){
//            throw new QuestionNotFoundException();
//        }
//        // Update question with new attributes
//        Question question = maybeQuestion.get();
////        question.setAvatar(input.getAvatar());
//        question.setQuestionname(modifiedQuestion.getQuestionname());
//        question.setPassword(modifiedQuestion.getPassword());
//        question.setEmail(modifiedQuestion.getEmail());
//        return questionRepository.save(question);
//    }
//
//    
//    public boolean existsByQuestionname(String questionname){
//        return questionRepository.existsByQuestionname(questionname);
//    }
//
//    
//    public boolean existsByEmail(String email){
//        return questionRepository.existsByEmail(email);
//    }
//
//   
//    public Question save(Question question) {
//        return questionRepository.save(question);
//    }
}
