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

import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.repository.ShortAnswerQuestionRepository;



@ExtendWith(MockitoExtension.class)
class ShortAnswerQuestionServiceTest {
	
	private ShortAnswerQuestionService saqService;
	private ShortAnswerQuestion saq;
	private Optional<ShortAnswerQuestion> opSaq;
	
	@Mock
	ShortAnswerQuestionRepository mockSaqRepo;
	
	@BeforeEach
	void setup() {
		saqService = new ShortAnswerQuestionService(mockSaqRepo);
		saq = new ShortAnswerQuestion();
	}

	@Test
	void test_findbyid_return_null_if_nothing_is_found() {
		Long id = (long) 1;
		opSaq = Optional.empty();
		when(mockSaqRepo.findById(id)).thenReturn(opSaq);
		
		ShortAnswerQuestion result = saqService.findById(id);
		
		verify(mockSaqRepo, times(1)).findById(id);
		assertNull(result);
	}
	
	@Test
	void test_findbyid_return_saq_if_an_saq_is_found() {
		Long id = (long) 1;
		opSaq = Optional.of(saq);
		when(mockSaqRepo.findById(id)).thenReturn(opSaq);
		
		ShortAnswerQuestion result = saqService.findById(id);
		
		verify(mockSaqRepo, times(1)).findById(id);
		assertEquals(saq, result);
	}
	
	@Test
	void test_find_all_call_saqRepo_findall() {
		
		List<ShortAnswerQuestion> saqs = new ArrayList<ShortAnswerQuestion>();
		saqs.add(saq);
		when(mockSaqRepo.findAll()).thenReturn(saqs);
		
		List<ShortAnswerQuestion> result = saqService.findAll();
		
		verify(mockSaqRepo, times(1)).findAll();
		assertTrue(result.contains(saq));
		assertEquals(result.size(), saqs.size());
	}

}
