package com.fdmgroup.QuizSystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

	private QuizController quizController;

	@Mock
	ModelToDTO mockModelToDTO;
	@Mock
	QuizService mockQuizService;
	@Mock
	QuestionService mockQuestionService;
	@Mock
	QuizQuestionGradeService mockQuizQuestionGradeService;

	@BeforeEach
	void setup() {
		quizController = new QuizController(mockModelToDTO, mockQuizService, mockQuestionService, mockQuizQuestionGradeService);
	}
	
	
	
}
