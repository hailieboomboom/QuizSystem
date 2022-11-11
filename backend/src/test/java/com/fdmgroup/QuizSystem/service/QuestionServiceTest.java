package com.fdmgroup.QuizSystem.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;



@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
	
	private QuestionService questionService;
	private Question question;
	private Optional<Question> opQuestion;
	
	@Mock
	QuestionRepository mockQuestionRepo;
	
	@BeforeEach
	void setup() {
		questionService = new QuestionService(mockQuestionRepo);
		question = new Question();
	}

	@Test
	void test_save_question_will_call_save_from_questionRepo() {
		questionService.save(question);
		
		verify(mockQuestionRepo, times(1)).save(question);
	}
	
	@Test
	void test_findAllQuestions_call_findall_from_questionRepo() {
		List<Question> questions = new ArrayList<Question>();
		questions.add(question);
		when(mockQuestionRepo.findAll()).thenReturn(questions);
		
		List<Question> result = questionService.findAllQuestions();
		
		verify(mockQuestionRepo, times(1)).findAll();
		assertTrue(result.contains(question));
		assertEquals(result.size(), questions.size());
	}
	
	@Test
	void test_findbyid_return_null_if_nothing_found_from_questionRepo() {
		Long id = (long) 1;
		opQuestion = Optional.empty();
		when(mockQuestionRepo.findById(id)).thenReturn(opQuestion);
		
		Question result = questionService.findById(id);
		
		verify(mockQuestionRepo, times(1)).findById(id);
		assertNull(result);
	}
	
	@Test
	void test_findbyid_return_foundquestion_if_question_found_from_questionRepo() {
		Long id = (long) 1;
		opQuestion = Optional.of(question);
		when(mockQuestionRepo.findById(id)).thenReturn(opQuestion);
		
		Question result = questionService.findById(id);
		
		verify(mockQuestionRepo, times(1)).findById(id);
		assertEquals(result, question);
	}
	
	@Test
	void test_remove_question_call_delete_from_questionRepo() {
		questionService.remove(question);
		
		verify(mockQuestionRepo, times(1)).delete(question);
	}
}
