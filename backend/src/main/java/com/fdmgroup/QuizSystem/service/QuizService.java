package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.exception.SalesCantCreateCourseQuizException;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizQuestionGradeService qqgService;
	
	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuizQuestionGradeService quizQuestionGradeService;

	@Autowired
	private QuizQuestionGradeRepository qqgRepository;
	
	
	public QuizDto createQuiz(QuizDto quizDto) {
		
		// Make sure Sales unable to make Course Quiz
		Optional<User> optionalUser = userRepository.findById(quizDto.getCreatorId());
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		QuizCategory quizCategory = quizDto.getQuizCategory();
		if(optionalUser.get()instanceof Sales && quizCategory == QuizCategory.COURSE_QUIZ ) {
			throw new SalesCantCreateCourseQuizException();
		}
		
		Quiz quizEntity = new Quiz();
		quizEntity.setName(quizDto.getName());
		quizEntity.setQuizCategory(quizDto.getQuizCategory());
//		quizEntity.setQuestions(quizDto.getQuestions());
		quizEntity.setCreator(userRepository.findById(quizDto.getCreatorId()).get());
		
		// TODO for Summer !!!!
		// let user know about quiz addition, but no need to let question know the Quiz
		
		Quiz managedQuiz = quizRepository.save(quizEntity);
		quizDto.setQuizId(managedQuiz.getId());
		return quizDto;
	}


	public Quiz save(Quiz quiz) {
		return quizRepository.save(quiz);
	}
	
	
	
	public QuizDto getQuizDto(Quiz quiz) {
		QuizDto quizDto = new QuizDto();
		quizDto.setQuizId(quiz.getId());
		quizDto.setCreatorId(quiz.getCreator().getId());
		quizDto.setName(quiz.getName());
		quizDto.setQuizCategory(quiz.getQuizCategory());
//		quizDto.setQuestions(quiz.getQuestions());
		return quizDto;
	}

	
	public List<QuizDto> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAll();
		
		List<QuizDto> quizDtos = new ArrayList<>();
		for (Quiz quiz : allQuizzes) {
			quizDtos.add(getQuizDto(quiz));
		}
		return quizDtos;
	}
	
	// update quiz details (not including questions)
	public void updateQuiz(long id, QuizDto quizDto){
		
		// Check if quiz exists
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			 throw new QuizNotFoundException();
		}

		
		Quiz quiz = optionalQuiz.get();
		quiz.setName(quizDto.getName());
		quiz.setQuizCategory(quizDto.getQuizCategory());
//		quiz.setQuestions(quizDto.getQuestions());
		quiz.setCreator(userRepository.findById(quizDto.getCreatorId()).get());
		
		
		quizRepository.save(quiz);
	}
	

	public void deleteQuizById(long id) {
		
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if(optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException();
		}
			
//		// TODO This part is handled by CascadeType.REMOVE on Quiz.java
//		// To be deleted by Summer: find all of the quizQuestionGrade associated with the quiz, loop and remove all of them.
//		List<QuizQuestionGrade> qqgsToRemove = qqgRepository.findByQuizId(id);
//		for(QuizQuestionGrade qqg: qqgsToRemove) {
//			qqgRepository.delete(qqg);
//		}
		
		quizRepository.deleteById(id); 
		
	}
	

	public Quiz getQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException();
		}
		return optionalQuiz.get();
	}
	

	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);

		
	}

	//TODO: parameters can be just Ids instead of entities? (from Yutta and Jason)
	public void addQuestionIntoQuiz(Question question, Quiz quiz, Float grade) {
		System.out.println("----ENTER ADDQUESTION: question id is "+question.getId()+" quiz id is "+ quiz.getId());
		
		question = questionRepo.findById(question.getId()).get();
		quiz = quizRepository.findById(quiz.getId()).get();
		QuizQuestionGradeKey qqgkey = new QuizQuestionGradeKey(quiz.getId(), question.getId());
		QuizQuestionGrade quizQuestion = new QuizQuestionGrade();
		quizQuestion.setKey(qqgkey);
		quizQuestion.setGrade(grade);
		quizQuestion.setQuestion(question);
		quizQuestion.setQuiz(quiz);
		System.out.println("after setkey try to get key: "+quizQuestion.getKey().getQuestionId()+"  "+quizQuestion.getKey().getQuizId());
		qqgService.save(quizQuestion);
	}

	public void removeQuestionFromQuiz(Question question, Quiz quiz) {
		QuizQuestionGradeKey qqgkey = new QuizQuestionGradeKey(quiz.getId(), question.getId());
		QuizQuestionGrade quizQuestion = qqgService.findById(qqgkey);
		if(quizQuestion != null) {
			qqgService.remove(quizQuestion);
		}
	}

	public List<QuizDto> getQuizzesByCreatorId(long creatorId) {
		
		Optional<User> optionalCreator = userRepository.findById(creatorId);
		
		if (optionalCreator.isEmpty()) {
			 throw new UserNotFoundException();
		}
		
		List<Quiz> quizzes = quizRepository.findByCreator(optionalCreator.get());

		List<QuizDto> quizDtos = new ArrayList<>();
		for(Quiz quiz: quizzes) {
			quizDtos.add(getQuizDto(quiz));
		}
		return quizDtos;
	}
	
	public float getMaxGrade (Long quizId) {
		Quiz quiz = getQuizById(quizId);
		float maxGrade = 0;
		for (QuizQuestionGrade qqg : quiz.getQuizQuestionsGrade()) {
			maxGrade += qqg.getGrade();
		}
		return maxGrade;
	}


}	


