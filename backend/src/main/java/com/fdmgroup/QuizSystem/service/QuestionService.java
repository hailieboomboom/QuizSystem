package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {

	@Autowired
    private QuestionRepository questionRepository;
	
	
	public QuestionService(QuestionRepository questionRepository) {
		super();
		this.questionRepository = questionRepository;
	}
	
	public Question save(Question question) {
	        return questionRepository.save(question);
	}

	public List<Question> findAllQuestions(){
		return questionRepository.findAll();
	}
	
	public Question findById(Long id) {
		Optional<Question> opQuestion = questionRepository.findById(id);
		if(opQuestion.isEmpty()) {
			return null;
		}
		return opQuestion.get();
	}
	
	public void remove(Question question) {
		questionRepository.delete(question);
	}
	
	public List<Question> findQuestionsByCreator(User creator){
		return questionRepository.findByCreator(creator);
	}


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
   
}
