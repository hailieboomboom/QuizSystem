package com.fdmgroup.QuizSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.exception.QuestionTagNotMatchQuizCategory;
import com.fdmgroup.QuizSystem.exception.QuizAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {QuizService.class})
@ExtendWith(SpringExtension.class)
class QuizServiceTest {
    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuizQuestionGradeService quizQuestionGradeService;

    @MockBean
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @MockBean
    private TagService tagService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link QuizService#createQuiz(QuizDto)}
     */
    @Test
    void testCreateQuiz() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        when(quizRepository.save((Quiz) any())).thenReturn(quiz);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user1);

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        QuizDto actualCreateQuizResult = quizService.createQuiz(quizDto);
        assertSame(quizDto, actualCreateQuizResult);
        assertEquals(123L, actualCreateQuizResult.getQuizId());
        verify(quizRepository).save((Quiz) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#createQuiz(QuizDto)}
     */
    @Test
    void testCreateQuiz2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        when(quizRepository.save((Quiz) any())).thenReturn(quiz);
        when(userService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        assertThrows(QuizNotFoundException.class, () -> quizService.createQuiz(quizDto));
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#save(Quiz)}
     */
    @Test
    void testSave() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        when(quizRepository.save((Quiz) any())).thenReturn(quiz);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user1);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        assertSame(quiz, quizService.save(quiz1));
        verify(quizRepository).save((Quiz) any());
    }

    /**
     * Method under test: {@link QuizService#save(Quiz)}
     */
    @Test
    void testSave2() {
        when(quizRepository.save((Quiz) any())).thenThrow(new QuizNotFoundException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        assertThrows(QuizNotFoundException.class, () -> quizService.save(quiz));
        verify(quizRepository).save((Quiz) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizDto(Quiz)}
     */
    @Test
    void testGetQuizDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        QuizDto actualQuizDto = quizService.getQuizDto(quiz);
        assertEquals(123L, actualQuizDto.getCreatorId());
        assertEquals(123L, actualQuizDto.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, actualQuizDto.getQuizCategory());
        assertEquals("Name", actualQuizDto.getName());
    }

    /**
     * Method under test: {@link QuizService#getQuizDto(Quiz)}
     */
    @Test
    void testGetQuizDto2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        Quiz quiz = mock(Quiz.class);
        when(quiz.getQuizCategory()).thenReturn(QuizCategory.COURSE_QUIZ);
        when(quiz.getName()).thenReturn("Name");
        when(quiz.getCreator()).thenReturn(user1);
        when(quiz.getId()).thenReturn(123L);
        doNothing().when(quiz).setCreator((User) any());
        doNothing().when(quiz).setId(anyLong());
        doNothing().when(quiz).setName((String) any());
        doNothing().when(quiz).setQuizCategory((QuizCategory) any());
        doNothing().when(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        QuizDto actualQuizDto = quizService.getQuizDto(quiz);
        assertEquals(123L, actualQuizDto.getCreatorId());
        assertEquals(123L, actualQuizDto.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, actualQuizDto.getQuizCategory());
        assertEquals("Name", actualQuizDto.getName());
        verify(quiz).getQuizCategory();
        verify(quiz).getCreator();
        verify(quiz).getName();
        verify(quiz).getId();
        verify(quiz).setCreator((User) any());
        verify(quiz).setId(anyLong());
        verify(quiz).setName((String) any());
        verify(quiz).setQuizCategory((QuizCategory) any());
        verify(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizDto(Quiz)}
     */
    @Test
    void testGetQuizDto3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        Quiz quiz = mock(Quiz.class);
        when(quiz.getQuizCategory()).thenThrow(new QuizAlreadyExistsException("An error occurred"));
        when(quiz.getName()).thenThrow(new QuizAlreadyExistsException("An error occurred"));
        when(quiz.getCreator()).thenReturn(user1);
        when(quiz.getId()).thenReturn(123L);
        doNothing().when(quiz).setCreator((User) any());
        doNothing().when(quiz).setId(anyLong());
        doNothing().when(quiz).setName((String) any());
        doNothing().when(quiz).setQuizCategory((QuizCategory) any());
        doNothing().when(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        assertThrows(QuizAlreadyExistsException.class, () -> quizService.getQuizDto(quiz));
        verify(quiz).getCreator();
        verify(quiz).getName();
        verify(quiz).getId();
        verify(quiz).setCreator((User) any());
        verify(quiz).setId(anyLong());
        verify(quiz).setName((String) any());
        verify(quiz).setQuizCategory((QuizCategory) any());
        verify(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
    }

    /**
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes() {
        when(quizRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(quizService.getAllQuizzes().isEmpty());
        verify(quizRepository).findAll();
    }

    /**
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        ArrayList<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        when(quizRepository.findAll()).thenReturn(quizList);
        List<QuizDto> actualAllQuizzes = quizService.getAllQuizzes();
        assertEquals(1, actualAllQuizzes.size());
        QuizDto getResult = actualAllQuizzes.get(0);
        assertEquals(123L, getResult.getCreatorId());
        assertEquals(123L, getResult.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, getResult.getQuizCategory());
        assertEquals("Name", getResult.getName());
        verify(quizRepository).findAll();
    }

    /**
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes3() {
        when(quizRepository.findAll()).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> quizService.getAllQuizzes());
        verify(quizRepository).findAll();
    }

    /**
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        Quiz quiz = mock(Quiz.class);
        when(quiz.getQuizCategory()).thenThrow(new QuizAlreadyExistsException("An error occurred"));
        when(quiz.getName()).thenThrow(new QuizAlreadyExistsException("An error occurred"));
        when(quiz.getCreator()).thenReturn(user1);
        when(quiz.getId()).thenReturn(123L);
        doNothing().when(quiz).setCreator((User) any());
        doNothing().when(quiz).setId(anyLong());
        doNothing().when(quiz).setName((String) any());
        doNothing().when(quiz).setQuizCategory((QuizCategory) any());
        doNothing().when(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        ArrayList<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        when(quizRepository.findAll()).thenReturn(quizList);
        assertThrows(QuizAlreadyExistsException.class, () -> quizService.getAllQuizzes());
        verify(quizRepository).findAll();
        verify(quiz).getCreator();
        verify(quiz).getName();
        verify(quiz).getId();
        verify(quiz).setCreator((User) any());
        verify(quiz).setId(anyLong());
        verify(quiz).setName((String) any());
        verify(quiz).setQuizCategory((QuizCategory) any());
        verify(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuiz(long, QuizDto)}
     */
    @Test
    void testUpdateQuiz() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user1);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        when(quizRepository.save((Quiz) any())).thenReturn(quiz1);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user2);

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        quizService.updateQuiz(123L, quizDto);
        verify(quizRepository).save((Quiz) any());
        verify(quizRepository).findById((Long) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#updateQuiz(long, QuizDto)}
     */
    @Test
    void testUpdateQuiz2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user1);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        when(quizRepository.save((Quiz) any())).thenReturn(quiz1);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        assertThrows(QuizNotFoundException.class, () -> quizService.updateQuiz(123L, quizDto));
        verify(quizRepository).findById((Long) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#updateQuiz(long, QuizDto)}
     */
    @Test
    void testUpdateQuiz3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        when(quizRepository.save((Quiz) any())).thenReturn(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user1);

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        assertThrows(QuizNotFoundException.class, () -> quizService.updateQuiz(123L, quizDto));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#deleteQuizById(long)}
     */
    @Test
    void testDeleteQuizById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quiz.setQuizQuestionsGrade(quizQuestionGradeList);
        Optional<Quiz> ofResult = Optional.of(quiz);
        doNothing().when(quizRepository).deleteById((Long) any());
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        quizService.deleteQuizById(123L);
        verify(quizRepository).findById((Long) any());
        verify(quizRepository).deleteById((Long) any());
        assertEquals(quizQuestionGradeList, quizService.getAllQuizzes());
    }

    /**
     * Method under test: {@link QuizService#deleteQuizById(long)}
     */
    @Test
    void testDeleteQuizById2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        doThrow(new QuizNotFoundException("An error occurred")).when(quizRepository).deleteById((Long) any());
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(QuizNotFoundException.class, () -> quizService.deleteQuizById(123L));
        verify(quizRepository).findById((Long) any());
        verify(quizRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#deleteQuizById(long)}
     */
    @Test
    void testDeleteQuizById3() {
        doNothing().when(quizRepository).deleteById((Long) any());
        when(quizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> quizService.deleteQuizById(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizById(long)}
     */
    @Test
    void testGetQuizById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(quiz, quizService.getQuizById(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizById(long)}
     */
    @Test
    void testGetQuizById2() {
        when(quizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> quizService.getQuizById(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizById(long)}
     */
    @Test
    void testGetQuizById3() {
        when(quizRepository.findById((Long) any())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> quizService.getQuizById(123L));
        verify(quizRepository).findById((Long) any());
    }

    
    /**
     * Method under test: {@link QuizService#addQuestionIntoQuiz(Question, Quiz, Float)}
     */
    @Test
    void testAddQuestionIntoQuiz() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);
        when(quizQuestionGradeService.save((QuizQuestionGrade) any())).thenReturn(quizQuestionGrade);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question1);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz1);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user4);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRole(Role.UNAUTHORISED_TRAINER);
        user5.setUsername("janedoe");

        Quiz quiz2 = new Quiz();
        quiz2.setCreator(user5);
        quiz2.setId(123L);
        quiz2.setName("Name");
        quiz2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz2.setQuizQuestionsGrade(new ArrayList<>());
        quizService.addQuestionIntoQuiz(question2, quiz2, 10.0f);
        verify(quizQuestionGradeService).save((QuizQuestionGrade) any());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#addQuestionIntoQuiz(Question, Quiz, Float)}
     */
    @Test
    void testAddQuestionIntoQuiz2() {
        when(quizQuestionGradeService.save((QuizQuestionGrade) any()))
                .thenThrow(new QuizNotFoundException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        assertThrows(QuizNotFoundException.class, () -> quizService.addQuestionIntoQuiz(question1, quiz1, 10.0f));
        verify(quizQuestionGradeService).save((QuizQuestionGrade) any());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#removeQuestionFromQuiz(Question, Quiz)}
     */
    @Test
    void testRemoveQuestionFromQuiz() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);
        doNothing().when(quizQuestionGradeService).remove((QuizQuestionGrade) any());
        when(quizQuestionGradeService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        quizService.removeQuestionFromQuiz(question1, quiz1);
        verify(quizQuestionGradeService).findById((QuizQuestionGradeKey) any());
        verify(quizQuestionGradeService).remove((QuizQuestionGrade) any());
    }

    /**
     * Method under test: {@link QuizService#removeQuestionFromQuiz(Question, Quiz)}
     */
    @Test
    void testRemoveQuestionFromQuiz2() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);
        doThrow(new QuizNotFoundException("An error occurred")).when(quizQuestionGradeService)
                .remove((QuizQuestionGrade) any());
        when(quizQuestionGradeService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        assertThrows(QuizNotFoundException.class, () -> quizService.removeQuestionFromQuiz(question1, quiz1));
        verify(quizQuestionGradeService).findById((QuizQuestionGradeKey) any());
        verify(quizQuestionGradeService).remove((QuizQuestionGrade) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId() {
        when(quizRepository.findByCreator((User) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user);
        assertTrue(quizService.getQuizzesByCreatorId(123L).isEmpty());
        verify(quizRepository).findByCreator((User) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId2() {
        when(quizRepository.findByCreator((User) any())).thenReturn(new ArrayList<>());
        when(userService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> quizService.getQuizzesByCreatorId(123L));
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        ArrayList<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        when(quizRepository.findByCreator((User) any())).thenReturn(quizList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user1);
        List<QuizDto> actualQuizzesByCreatorId = quizService.getQuizzesByCreatorId(123L);
        assertEquals(1, actualQuizzesByCreatorId.size());
        QuizDto getResult = actualQuizzesByCreatorId.get(0);
        assertEquals(123L, getResult.getCreatorId());
        assertEquals(123L, getResult.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, getResult.getQuizCategory());
        assertEquals("Name", getResult.getName());
        verify(quizRepository).findByCreator((User) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        Quiz quiz = mock(Quiz.class);
        when(quiz.getQuizCategory()).thenThrow(new QuizAlreadyExistsException("An error occurred"));
        when(quiz.getName()).thenThrow(new QuizAlreadyExistsException("An error occurred"));
        when(quiz.getCreator()).thenReturn(user1);
        when(quiz.getId()).thenReturn(123L);
        doNothing().when(quiz).setCreator((User) any());
        doNothing().when(quiz).setId(anyLong());
        doNothing().when(quiz).setName((String) any());
        doNothing().when(quiz).setQuizCategory((QuizCategory) any());
        doNothing().when(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        ArrayList<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        when(quizRepository.findByCreator((User) any())).thenReturn(quizList);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user2);
        assertThrows(QuizAlreadyExistsException.class, () -> quizService.getQuizzesByCreatorId(123L));
        verify(quizRepository).findByCreator((User) any());
        verify(quiz).getCreator();
        verify(quiz).getName();
        verify(quiz).getId();
        verify(quiz).setCreator((User) any());
        verify(quiz).setId(anyLong());
        verify(quiz).setName((String) any());
        verify(quiz).setQuizCategory((QuizCategory) any());
        verify(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals(0.0f, quizService.getMaxGrade(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user1);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user2);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(quizQuestionGradeList);
        Optional<Quiz> ofResult = Optional.of(quiz1);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals(10.0f, quizService.getMaxGrade(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade3() {
        when(quizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> quizService.getMaxGrade(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade4() {
        when(quizRepository.findById((Long) any())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> quizService.getMaxGrade(123L));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user);
        assertThrows(UserUnauthorisedError.class,
                () -> quizService.checkAccessToQuizCategory(QuizCategory.COURSE_QUIZ, 123L));
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.AUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user);
        quizService.checkAccessToQuizCategory(QuizCategory.COURSE_QUIZ, 123L);
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user);
        assertThrows(UserUnauthorisedError.class,
                () -> quizService.checkAccessToQuizCategory(QuizCategory.INTERVIEW_QUIZ, 123L));
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory4() {
        when(userService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class,
                () -> quizService.checkAccessToQuizCategory(QuizCategory.COURSE_QUIZ, 123L));
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user1);
        quizService.checkAccessToQuizId(123L, 123L);
        verify(quizRepository).findById((Long) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> quizService.checkAccessToQuizId(123L, 123L));
        verify(quizRepository).findById((Long) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        Sales sales = mock(Sales.class);
        when(sales.getId()).thenReturn(1L);
        doNothing().when(sales).setRole((Role) any());
        doNothing().when(sales).setEmail((String) any());
        doNothing().when(sales).setFirstName((String) any());
        doNothing().when(sales).setId(anyLong());
        doNothing().when(sales).setLastName((String) any());
        doNothing().when(sales).setPassword((String) any());
        doNothing().when(sales).setUsername((String) any());
        sales.setEmail("jane.doe@example.org");
        sales.setFirstName("Jane");
        sales.setId(123L);
        sales.setLastName("Doe");
        sales.setPassword("iloveyou");
        sales.setRole(Role.UNAUTHORISED_TRAINER);
        sales.setUsername("janedoe");
        Quiz quiz = mock(Quiz.class);
        when(quiz.getCreator()).thenReturn(sales);
        doNothing().when(quiz).setCreator((User) any());
        doNothing().when(quiz).setId(anyLong());
        doNothing().when(quiz).setName((String) any());
        doNothing().when(quiz).setQuizCategory((QuizCategory) any());
        doNothing().when(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user1);
        assertThrows(UserUnauthorisedError.class, () -> quizService.checkAccessToQuizId(123L, 123L));
        verify(quizRepository).findById((Long) any());
        verify(quiz).getCreator();
        verify(quiz).setCreator((User) any());
        verify(quiz).setId(anyLong());
        verify(quiz).setName((String) any());
        verify(quiz).setQuizCategory((QuizCategory) any());
        verify(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        verify(sales).getId();
        verify(sales).setRole((Role) any());
        verify(sales).setEmail((String) any());
        verify(sales).setFirstName((String) any());
        verify(sales).setId(anyLong());
        verify(sales).setLastName((String) any());
        verify(sales).setPassword((String) any());
        verify(sales).setUsername((String) any());
        verify(userService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId4() {
        when(quizRepository.findById((Long) any())).thenReturn(Optional.empty());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        Sales sales = mock(Sales.class);
        when(sales.getId()).thenReturn(123L);
        doNothing().when(sales).setRole((Role) any());
        doNothing().when(sales).setEmail((String) any());
        doNothing().when(sales).setFirstName((String) any());
        doNothing().when(sales).setId(anyLong());
        doNothing().when(sales).setLastName((String) any());
        doNothing().when(sales).setPassword((String) any());
        doNothing().when(sales).setUsername((String) any());
        sales.setEmail("jane.doe@example.org");
        sales.setFirstName("Jane");
        sales.setId(123L);
        sales.setLastName("Doe");
        sales.setPassword("iloveyou");
        sales.setRole(Role.UNAUTHORISED_TRAINER);
        sales.setUsername("janedoe");
        Quiz quiz = mock(Quiz.class);
        when(quiz.getCreator()).thenReturn(sales);
        doNothing().when(quiz).setCreator((User) any());
        doNothing().when(quiz).setId(anyLong());
        doNothing().when(quiz).setName((String) any());
        doNothing().when(quiz).setQuizCategory((QuizCategory) any());
        doNothing().when(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(userService.getUserById(anyLong())).thenReturn(user1);
        assertThrows(QuizNotFoundException.class, () -> quizService.checkAccessToQuizId(123L, 123L));
        verify(quizRepository).findById((Long) any());
        verify(quiz).setCreator((User) any());
        verify(quiz).setId(anyLong());
        verify(quiz).setName((String) any());
        verify(quiz).setQuizCategory((QuizCategory) any());
        verify(quiz).setQuizQuestionsGrade((List<QuizQuestionGrade>) any());
        verify(sales).setRole((Role) any());
        verify(sales).setEmail((String) any());
        verify(sales).setFirstName((String) any());
        verify(sales).setId(anyLong());
        verify(sales).setLastName((String) any());
        verify(sales).setPassword((String) any());
        verify(sales).setUsername((String) any());
    }

    /**
     * Method under test: {@link QuizService#checkQuestionTagMatchQuizCategory(QuizCategory, Question)}
     */
    @Test
    void testCheckQuestionTagMatchQuizCategory() {
        Tag tag = new Tag();
        tag.setId(123L);
        tag.setTagName("Tag Name");
        tag.setTutorials(new HashSet<>());
        when(tagService.getTagByName((String) any())).thenReturn(tag);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());
        assertThrows(QuestionTagNotMatchQuizCategory.class,
                () -> quizService.checkQuestionTagMatchQuizCategory(QuizCategory.COURSE_QUIZ, question));
        verify(tagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#checkQuestionTagMatchQuizCategory(QuizCategory, Question)}
     */
    @Test
    void testCheckQuestionTagMatchQuizCategory2() {
        Tag tag = new Tag();
        tag.setId(123L);
        tag.setTagName("Tag Name");
        tag.setTutorials(new HashSet<>());
        when(tagService.getTagByName((String) any())).thenReturn(tag);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());
        assertThrows(QuestionTagNotMatchQuizCategory.class,
                () -> quizService.checkQuestionTagMatchQuizCategory(QuizCategory.INTERVIEW_QUIZ, question));
        verify(tagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#checkQuestionTagMatchQuizCategory(QuizCategory, Question)}
     */
    @Test
    void testCheckQuestionTagMatchQuizCategory3() {
        when(tagService.getTagByName((String) any())).thenThrow(new QuizNotFoundException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());
        assertThrows(QuizNotFoundException.class,
                () -> quizService.checkQuestionTagMatchQuizCategory(QuizCategory.COURSE_QUIZ, question));
        verify(tagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        quizService.createQuizQuestions(1L, new ArrayList<>());
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions2() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong()))
                .thenThrow(new QuizNotFoundException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(QuizNotFoundException.class, () -> quizService.createQuizQuestions(1L, new ArrayList<>()));
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions3() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user2);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz1);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(QuizAlreadyExistsException.class, () -> quizService.createQuizQuestions(1L, new ArrayList<>()));
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions4() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());
        when(quizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> quizService.createQuizQuestions(1L, new ArrayList<>()));
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions5() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);

        Tag tag = new Tag();
        tag.setId(123L);
        tag.setTagName("Tag Name");
        tag.setTutorials(new HashSet<>());
        when(tagService.getTagByName((String) any())).thenReturn(tag);

        QuestionGradeDTO questionGradeDTO = new QuestionGradeDTO();
        questionGradeDTO.setGrade(10.0f);
        questionGradeDTO.setQuestionDetails("Question Details");
        questionGradeDTO.setQuestionId(123L);
        questionGradeDTO.setTags(new HashSet<>());

        ArrayList<QuestionGradeDTO> questionGradeDTOList = new ArrayList<>();
        questionGradeDTOList.add(questionGradeDTO);
        assertThrows(QuestionTagNotMatchQuizCategory.class,
                () -> quizService.createQuizQuestions(1L, questionGradeDTOList));
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
        verify(tagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions6() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        when(tagService.getTagByName((String) any())).thenThrow(new QuizNotFoundException("An error occurred"));

        QuestionGradeDTO questionGradeDTO = new QuestionGradeDTO();
        questionGradeDTO.setGrade(10.0f);
        questionGradeDTO.setQuestionDetails("Question Details");
        questionGradeDTO.setQuestionId(123L);
        questionGradeDTO.setTags(new HashSet<>());

        ArrayList<QuestionGradeDTO> questionGradeDTOList = new ArrayList<>();
        questionGradeDTOList.add(questionGradeDTO);
        assertThrows(QuizNotFoundException.class, () -> quizService.createQuizQuestions(1L, questionGradeDTOList));
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
        verify(tagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        quizService.updateQuizQuestions(1L, new ArrayList<>());
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions2() {
        when(quizQuestionGradeService.findAllByQuizId(anyLong()))
                .thenThrow(new QuizNotFoundException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(QuizNotFoundException.class, () -> quizService.updateQuizQuestions(1L, new ArrayList<>()));
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions3() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);

        QuizQuestionGradeKey quizQuestionGradeKey1 = new QuizQuestionGradeKey();
        quizQuestionGradeKey1.setQuestionId(123L);
        quizQuestionGradeKey1.setQuizId(123L);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade1 = new QuizQuestionGrade();
        quizQuestionGrade1.setGrade(10.0f);
        quizQuestionGrade1.setKey(quizQuestionGradeKey1);
        quizQuestionGrade1.setQuestion(question1);
        quizQuestionGrade1.setQuiz(quiz1);
        doNothing().when(quizQuestionGradeService).remove((QuizQuestionGrade) any());
        when(quizQuestionGradeService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade1);
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user4);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question2);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRole(Role.UNAUTHORISED_TRAINER);
        user5.setUsername("janedoe");

        Quiz quiz2 = new Quiz();
        quiz2.setCreator(user5);
        quiz2.setId(123L);
        quiz2.setName("Name");
        quiz2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz2.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz2);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        quizService.updateQuizQuestions(1L, new ArrayList<>());
        verify(quizQuestionGradeService).findById((QuizQuestionGradeKey) any());
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizQuestionGradeService).remove((QuizQuestionGrade) any());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions4() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);

        QuizQuestionGradeKey quizQuestionGradeKey1 = new QuizQuestionGradeKey();
        quizQuestionGradeKey1.setQuestionId(123L);
        quizQuestionGradeKey1.setQuizId(123L);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade1 = new QuizQuestionGrade();
        quizQuestionGrade1.setGrade(10.0f);
        quizQuestionGrade1.setKey(quizQuestionGradeKey1);
        quizQuestionGrade1.setQuestion(question1);
        quizQuestionGrade1.setQuiz(quiz1);
        doThrow(new QuizNotFoundException("An error occurred")).when(quizQuestionGradeService)
                .remove((QuizQuestionGrade) any());
        when(quizQuestionGradeService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade1);
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user4);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question2);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRole(Role.UNAUTHORISED_TRAINER);
        user5.setUsername("janedoe");

        Quiz quiz2 = new Quiz();
        quiz2.setCreator(user5);
        quiz2.setId(123L);
        quiz2.setName("Name");
        quiz2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz2.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz2);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(QuizNotFoundException.class, () -> quizService.updateQuizQuestions(1L, new ArrayList<>()));
        verify(quizQuestionGradeService).findById((QuizQuestionGradeKey) any());
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizQuestionGradeService).remove((QuizQuestionGrade) any());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions5() {
        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");

        Question question = new Question();
        question.setCreator(user);
        question.setId(123L);
        question.setQuestionDetails("Question Details");
        question.setTags(new HashSet<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(user1);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz);

        QuizQuestionGradeKey quizQuestionGradeKey1 = new QuizQuestionGradeKey();
        quizQuestionGradeKey1.setQuestionId(123L);
        quizQuestionGradeKey1.setQuizId(123L);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user2);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user3);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade1 = new QuizQuestionGrade();
        quizQuestionGrade1.setGrade(10.0f);
        quizQuestionGrade1.setKey(quizQuestionGradeKey1);
        quizQuestionGrade1.setQuestion(question1);
        quizQuestionGrade1.setQuiz(quiz1);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade1);
        quizQuestionGradeList.add(quizQuestionGrade);

        QuizQuestionGradeKey quizQuestionGradeKey2 = new QuizQuestionGradeKey();
        quizQuestionGradeKey2.setQuestionId(123L);
        quizQuestionGradeKey2.setQuizId(123L);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user4);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRole(Role.UNAUTHORISED_TRAINER);
        user5.setUsername("janedoe");

        Quiz quiz2 = new Quiz();
        quiz2.setCreator(user5);
        quiz2.setId(123L);
        quiz2.setName("Name");
        quiz2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz2.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade2 = new QuizQuestionGrade();
        quizQuestionGrade2.setGrade(10.0f);
        quizQuestionGrade2.setKey(quizQuestionGradeKey2);
        quizQuestionGrade2.setQuestion(question2);
        quizQuestionGrade2.setQuiz(quiz2);
        doNothing().when(quizQuestionGradeService).remove((QuizQuestionGrade) any());
        when(quizQuestionGradeService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade2);
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setFirstName("Jane");
        user6.setId(123L);
        user6.setLastName("Doe");
        user6.setPassword("iloveyou");
        user6.setRole(Role.UNAUTHORISED_TRAINER);
        user6.setUsername("janedoe");

        Question question3 = new Question();
        question3.setCreator(user6);
        question3.setId(123L);
        question3.setQuestionDetails("Question Details");
        question3.setTags(new HashSet<>());
        when(questionService.findById((Long) any())).thenReturn(question3);

        User user7 = new User();
        user7.setEmail("jane.doe@example.org");
        user7.setFirstName("Jane");
        user7.setId(123L);
        user7.setLastName("Doe");
        user7.setPassword("iloveyou");
        user7.setRole(Role.UNAUTHORISED_TRAINER);
        user7.setUsername("janedoe");

        Quiz quiz3 = new Quiz();
        quiz3.setCreator(user7);
        quiz3.setId(123L);
        quiz3.setName("Name");
        quiz3.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz3.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz3);
        when(quizRepository.findById((Long) any())).thenReturn(ofResult);
        quizService.updateQuizQuestions(1L, new ArrayList<>());
        verify(quizQuestionGradeService).findById((QuizQuestionGradeKey) any());
        verify(quizQuestionGradeService).findAllByQuizId(anyLong());
        verify(quizQuestionGradeService).remove((QuizQuestionGrade) any());
        verify(questionService).findById((Long) any());
        verify(quizRepository).findById((Long) any());
    }
}

