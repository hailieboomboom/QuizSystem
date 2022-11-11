package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;

@Service
@Transactional
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizQuestionGradeService qqgService;
	
	@Autowired
	private QuestionRepository questionRepo;

	public Quiz save(Quiz quizToSave) {
		return quizRepository.save(quizToSave);
	}

	public Quiz updateQuiz(long id, Quiz quizToUpdate) {
		Optional<Quiz> quizInRepo = quizRepository.findById(id);
		if (quizInRepo.isEmpty()) {
			throw new QuizNotFoundException();
		}

		// Update quiz with new attributes
		Quiz managedQuiz = quizInRepo.get();
		managedQuiz.setQuizCategory(quizToUpdate.getQuizCategory());
		return managedQuiz;
	}

	public void deleteQuizById(long id) {
		if (quizRepository.existsById(id)) {
			quizRepository.deleteById(id);
		} else {
			throw new QuizNotFoundException();
		}
	}

	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}

	public Quiz getQuizById(long id) {

		Optional<Quiz> quizInRepo = quizRepository.findById(id);
		if (quizInRepo.isEmpty()) {
			throw new QuizNotFoundException();
		}
		return quizInRepo.get();
	}


	
	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);
		
	}
	
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

	
}
