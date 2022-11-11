package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.QuizRequest;
import com.fdmgroup.QuizSystem.dto.QuizResponse;
import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class QuizService {

	private QuizRepository quizRepository;
	private UserRepository userRepository;

	public void createQuiz(QuizRequest quizRequest) {
		
		Quiz quizEntity = new Quiz();
		quizEntity.setName(quizRequest.getName());
		quizEntity.setQuizCategory(quizRequest.getQuizCategory());
//		quizEntity.setQuestions(quizRequest.getQuestions());
		quizEntity.setCreator(userRepository.findById(quizRequest.getCreatorId()).get());
		
		// TODO: !!!!
		// let user know about quiz addition, but no need to let question know the Quiz
		
		quizRepository.save(quizEntity);
	}

//  // TODO: To be deleted once confirm everything works
//	public QuizDto getQuizDto(Quiz quiz) {
//		QuizDto quizDto = new QuizDto();
//		quizDto.setQuizId(quiz.getId());
//		quizDto.setCreatorId(quiz.getCreator().getId());
//		quizDto.setName(quiz.getName());
//		quizDto.setQuizCategory(quiz.getQuizCategory());
////		quizDto.setQuestions(quiz.getQuestions());
//		return quizDto;
//	}
//	
//	public List<QuizDto> getAllQuizzes() {
//	List<Quiz> allQuizzes = quizRepository.findAll();
//	
//	List<QuizDto> quizDtos = new ArrayList<>();
//	for (Quiz quiz : allQuizzes) {
//		quizDtos.add(getQuizDto(quiz));
//	}
//	
//	return quizDtos;
//}
	
	
	public QuizResponse getQuizResponse(Quiz quiz) {
		QuizResponse quizResponse = new QuizResponse();
		quizResponse.setQuizId(quiz.getId());
		quizResponse.setCreatorId(quiz.getCreator().getId());
		quizResponse.setName(quiz.getName());
		quizResponse.setQuizCategory(quiz.getQuizCategory());
//		quizResponse.setQuestions(quiz.getQuestions());
		return quizResponse;
	}

	
	public List<QuizResponse> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAll();
		
		List<QuizResponse> quizResponses = new ArrayList<>();
		for (Quiz quiz : allQuizzes) {
			quizResponses.add(getQuizResponse(quiz));
		}
		return quizResponses;
	}
	
	public void updateQuiz(long id, QuizRequest quizRequest){
		
		// Check if quiz exists
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			 throw new QuizNotFoundException();
		}

		
		Quiz quiz = optionalQuiz.get();
		quiz.setName(quizRequest.getName());
		quiz.setQuizCategory(quizRequest.getQuizCategory());
//		quiz.setQuestions(quizRequest.getQuestions());
		quiz.setCreator(userRepository.findById(quizRequest.getCreatorId()).get());
		
		// TODO !!!!!!!!
		// UPDATE QUETION LIST !!!! 
		
		quizRepository.save(quiz);
	}
	

	public void deleteQuizById(long id) {
		
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if(optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException();
		}
		Quiz managedQuiz = optionalQuiz.get();
		
	// TODO is it necessary to do these steps?	
//		// let user know about quiz removal
//		User managedUser = managedQuiz.getCreator();
//		managedUser.removeQuiz(managedQuiz);
//		userRepo.save(managedUser);
//		
//		// let questions know about quiz removal
//		List<Question> managedQuestions = managedQuiz.getQuestions();
//		for(Question managedQuestion: managedQuestions) {
//			managedQuestion.removeQuiz(managedQuiz);
//			questionRepository.save(managedQuestion);
//		}
		
		quizRepository.deleteById(id);
		
		// TODO or can it be simplify in this way?
//		if (quizRepository.existsById(id)) {
//			quizRepository.deleteById(id);
//		} else {
//			throw new QuizNotFoundException();
//		}
	}
	

	public QuizResponse getQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException();
		}
		return getQuizResponse(optionalQuiz.get());
	}
	

	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);

	}
	
//  // TODO: to be completed
//	public List<QuizResponse> getContentQuizzes() {
//		
//		List<Quiz> contentQuizzes = quizRepository.findByQuizCategory(QuizCategory.COURSE_QUIZ);
//		
//		List<QuizResponse> contentQuizResponses = new ArrayList<>();
//		for (Quiz quiz : contentQuizzes) {
//			contentQuizResponses.add(getQuizResponse(quiz));
//		}
//		return contentQuizResponses;
//	}
	


}
