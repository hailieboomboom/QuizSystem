package com.fdmgroup.QuizSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

	private QuizService quizService;
	private Quiz quiz;

	@Mock
	QuizRepository mockQuizRepository;
	@Mock
	QuizQuestionGradeService mockQqgService;
	@Mock
	QuestionRepository mockQuestionRepository;
	@Mock
	UserRepository mockUserRepository;
	@Mock
	QuizQuestionGradeService mockQuizQuestionGradeService;
	@Mock
	UserService mockUserService;
	@Mock
	QuestionService mockQuestionService;
	@Mock
	TagService mockTagService;
	@Mock
	QuestionRepository mockQuestionRepo;

	@BeforeEach
	void setup() {
		quizService = new QuizService(mockQuizRepository, mockQqgService, mockQuestionRepo, mockUserRepository,
				mockQuizQuestionGradeService, mockUserService, mockQuestionService, mockTagService);
		quiz = new Quiz();
	}

}
