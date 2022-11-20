package com.fdmgroup.QuizSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.exception.QuizAttemptNotFoundException;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;

@ExtendWith(MockitoExtension.class)
class QuizAttemptServiceTest {
	
	private QuizAttemptService quizAttemptService;
	
	@Mock
	private QuizAttemptRepository mockQuizAttemptRepository;
	
	@Mock
	private QuizQuestionMCQAttemptRepository mockMcqAttemptRepository;

	@Mock
	private UserService mockUserService;

	@Mock
	private QuizAttempt mockQuizAttempt;

	@Mock
	private Quiz mockQuiz;

	@Mock
	private User mockUser;

	@Mock
	private QuizQuestionMCQAttempt mockMCQAttempt;

	@Mock
	private QuizAttempt mockQuizAttemptDB;

	@Mock
	private MCQAttemptDTO mockMCQAttemptDTO;

	@Mock
	private MultipleChoiceQuestion mockMCQ;

	@Mock
	private MultipleChoiceOption mockMCQOption;

	@BeforeEach
	void setUp() {
		quizAttemptService = new QuizAttemptService(mockQuizAttemptRepository, mockMcqAttemptRepository, mockUserService);
	}

	@Test
	void testSave() {
		when(mockQuizAttemptRepository.save(mockQuizAttempt)).thenReturn(mockQuizAttemptDB);
		
		QuizAttempt result = quizAttemptService.save(mockQuizAttempt);
		
		verify(mockQuizAttemptRepository).save(mockQuizAttempt);
		assertEquals(mockQuizAttemptDB, result);
	}

	@Test
	void testFindQuizAttemptById() {
		long id = 1;
		when(mockQuizAttemptRepository.findById(id)).thenReturn(Optional.of(mockQuizAttempt));
		
		QuizAttempt result = quizAttemptService.findQuizAttemptById(id);
		
		assertEquals(mockQuizAttempt, result);
	}

	@Test
	void testFindQuizAttemptById_ThrowsQuizAttemptNotFoundException() {
		long id = 1;
		when(mockQuizAttemptRepository.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(QuizAttemptNotFoundException.class, () -> quizAttemptService.findQuizAttemptById(id));
    }

	@Test
	void testcalculateNumberOfAttempts() {
		long id = 1;
		long user_id = 1;
		List<QuizAttempt> QAList = new ArrayList<>();
		QAList.add(mockQuizAttempt);
		QAList.add(mockQuizAttempt);
		QAList.add(mockQuizAttempt);
		when(mockQuizAttemptRepository.findById(id)).thenReturn(Optional.of(mockQuizAttempt));
		when(mockQuizAttempt.getQuiz()).thenReturn(mockQuiz);
		when(mockQuiz.getId()).thenReturn(id);
		when(mockQuizAttempt.getUser()).thenReturn(mockUser);
		when(mockUser.getId()).thenReturn(user_id);
		when(mockQuizAttemptRepository.findByQuizIdAndUserId(id, user_id)).thenReturn(QAList);
		
		int result = quizAttemptService.calculateNumberOfAttempts(id);
		
		assertEquals(QAList.size(), result);
	}

	@Test
	void testCalculateTotalAwarded() {
		long id = 1;
		List<QuizQuestionMCQAttempt> attemptList = new ArrayList<>();
		attemptList.add(mockMCQAttempt);
		attemptList.add(mockMCQAttempt);
		attemptList.add(mockMCQAttempt);
		when(mockMcqAttemptRepository.findByQuizAttemptId(id)).thenReturn(attemptList);
		when(mockMCQAttempt.getAwarded_grade()).thenReturn((float) 1);
		
		float result = quizAttemptService.calculateTotalAwarded(id);
		
		assertEquals(3.0, result);
	}

	@Test
	void testFindQuizAttemptByQuizId() {
		long id = 1;
		List<QuizAttempt> QAList = new ArrayList<>();
		QAList.add(mockQuizAttempt);
		QAList.add(mockQuizAttempt);
		QAList.add(mockQuizAttempt);
		when(mockQuizAttemptRepository.findByQuizId(id)).thenReturn(QAList);
		
		List<QuizAttempt> result = quizAttemptService.findQuizAttemptByQuizId(id);
		
		verify(mockQuizAttemptRepository).findByQuizId(id);
		assertEquals(QAList, result);
	}
	
	@Test
	void testDeleteAttempt() {

		List<QuizQuestionMCQAttempt> attemptList = new ArrayList<>();
		attemptList.add(mockMCQAttempt);
		attemptList.add(mockMCQAttempt);
		attemptList.add(mockMCQAttempt);
		when(mockMcqAttemptRepository.findByQuizAttempt(mockQuizAttempt)).thenReturn(attemptList);
		
		quizAttemptService.deleteAttempt(mockQuizAttempt);
		
		verify(mockMcqAttemptRepository,atLeastOnce()).findByQuizAttempt(mockQuizAttempt);
		verify(mockMcqAttemptRepository,times(3)).delete(mockMCQAttempt);
		verify(mockQuizAttemptRepository).delete(mockQuizAttempt);
	}

	@Test
	void testFindQuizAttemptByUser() {
		List<QuizAttempt> QAList = new ArrayList<>();
		QAList.add(mockQuizAttempt);
		QAList.add(mockQuizAttempt);
		QAList.add(mockQuizAttempt);
		when(mockQuizAttemptRepository.findByUser(mockUser)).thenReturn(QAList);
		
		List<QuizAttempt> result = quizAttemptService.findQuizAttemptByUser(mockUser);
		
		verify(mockQuizAttemptRepository).findByUser(mockUser);
		assertEquals(QAList, result);
	}

	@Test
	void testGetMCQAttemptsforOneQuizAttempt() {
		List<MCQAttemptDTO> mcqDTOList = new ArrayList<>();
		MCQAttemptDTO mcqAttemptDTO = new MCQAttemptDTO();
		mcqAttemptDTO.setMcqId(0);
		mcqAttemptDTO.setAwarded_grade(0);
		mcqAttemptDTO.setSelectedOption(0);
		mcqAttemptDTO.setQuizAttemptId(0);
		mcqDTOList.add(mcqAttemptDTO);
		mcqDTOList.add(mcqAttemptDTO);
		mcqDTOList.add(mcqAttemptDTO);
		List<QuizQuestionMCQAttempt> MCQAList = new ArrayList<>();
		MCQAList.add(mockMCQAttempt);
		MCQAList.add(mockMCQAttempt);
		MCQAList.add(mockMCQAttempt);
		when(mockMcqAttemptRepository.findByQuizAttempt(mockQuizAttempt)).thenReturn(MCQAList);
		when(mockMCQAttempt.getMultipleChoiceQuestion()).thenReturn(mockMCQ);
		when(mockMCQAttempt.getSelectedOption()).thenReturn(mockMCQOption);
		when(mockMCQAttempt.getQuizAttempt()).thenReturn(mockQuizAttempt);
		
		List<MCQAttemptDTO> result = quizAttemptService.getMCQAttemptsforOneQuizAttempt(mockQuizAttempt);
		
		verify(mockMcqAttemptRepository).findByQuizAttempt(mockQuizAttempt);
		assertEquals(mcqDTOList, result);
	}

	@Test
	void testCheckAccessToQuizCategory_ThrowsUserUnauthorisedError_AsSales() {
		
		QuizCategory requestQuizCategory = QuizCategory.COURSE_QUIZ;
		long activeUserId = 1;
		when(mockUserService.getUserById(activeUserId)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn(Role.AUTHORISED_SALES);
		
		
		assertThrows(UserUnauthorisedError.class, () -> quizAttemptService.checkAccessToQuizCategory(requestQuizCategory, activeUserId));
	}

	@Test
	void testCheckAccessToQuizCategory_ThrowsUserUnauthorisedError_AsTraining() {
		
		QuizCategory requestQuizCategory = QuizCategory.INTERVIEW_QUIZ;
		long activeUserId = 1;
		when(mockUserService.getUserById(activeUserId)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn(Role.TRAINING);
		
		
		assertThrows(UserUnauthorisedError.class, () -> quizAttemptService.checkAccessToQuizCategory(requestQuizCategory, activeUserId));
	}

}
