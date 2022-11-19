package com.fdmgroup.QuizSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;

@ExtendWith(MockitoExtension.class)
class QuizQuestionGradeServiceTest {

	private QuizQuestionGradeService quizQuestionGradeService;


	@Mock
	QuizQuestionGradeRepository mockQqgRepository;
	
	@BeforeEach
	void setup() {
		quizQuestionGradeService = new QuizQuestionGradeService(mockQqgRepository);
	}

}
