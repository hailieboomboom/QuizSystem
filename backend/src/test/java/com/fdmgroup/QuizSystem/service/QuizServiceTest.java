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
import com.fdmgroup.QuizSystem.exception.*;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizRepository;

import java.util.*;


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
    private QuestionService mockQuestionService;

    @MockBean
    private QuizQuestionGradeService mockQqgService;

    @MockBean
    private QuizRepository mockQuizRepository;

    @Autowired
    private QuizService mockQuizService;

    @MockBean
    private TagService mockTagService;

    @MockBean
    private UserService mockUserService;

    /**
     * Method under test: {@link QuizService#createQuiz(QuizDto)}
     */
    @Test
    void testCreateQuiz_returnsQuizDto_whenUserCanBeFoundInDatabase_andQuizIsSavedToDatabase() {
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
        when(mockQuizRepository.save((Quiz) any())).thenReturn(quiz);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user1);

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        QuizDto actualCreateQuizResult = mockQuizService.createQuiz(quizDto);
        assertSame(quizDto, actualCreateQuizResult);
        assertEquals(123L, actualCreateQuizResult.getQuizId());
        verify(mockQuizRepository).save((Quiz) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#createQuiz(QuizDto)}
     */
    @Test
    void testCreateQuiz_throwsQuizNotFoundException_whenUserCanNotBeFoundInDatabase() {
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
        when(mockQuizRepository.save((Quiz) any())).thenReturn(quiz);
        when(mockUserService.getUserById(anyLong())).thenThrow(new UserNotFoundException("An error occurred"));

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        assertThrows(UserNotFoundException.class, () -> mockQuizService.createQuiz(quizDto));
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#save(Quiz)}
     */
    @Test
    void testSave_returnsQuizThatIsSavedToDatabase() {
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
        when(mockQuizRepository.save((Quiz) any())).thenReturn(quiz);

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
        assertSame(quiz, mockQuizService.save(quiz1));
        verify(mockQuizRepository).save((Quiz) any());
    }

    /**
     * Method under test: {@link QuizService#save(Quiz)}
     */
    @Test
    void testSave_throwsException_whenQuizCanNotBeSavedToDatabase() {
        when(mockQuizRepository.save((Quiz) any())).thenThrow(new QuizNotFoundException("An error occurred"));

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
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.save(quiz));
        verify(mockQuizRepository).save((Quiz) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizDto(Quiz)}
     */
    @Test
    void testGetQuizDto_returnsQuizDto() {
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
        QuizDto actualQuizDto = mockQuizService.getQuizDto(quiz);
        assertEquals(123L, actualQuizDto.getCreatorId());
        assertEquals(123L, actualQuizDto.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, actualQuizDto.getQuizCategory());
        assertEquals("Name", actualQuizDto.getName());
    }

    /**
     * Method under test: {@link QuizService#getQuizDto(Quiz)}
     */
    @Test
    void testGetQuizDto_returnsQuizDto_andGoesThroughAllSetterMethods() {
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
        QuizDto actualQuizDto = mockQuizService.getQuizDto(quiz);
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
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes_returnsEmptyList_whenNoQuizzesAreFound() {
        when(mockQuizRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(mockQuizService.getAllQuizzes().isEmpty());
        verify(mockQuizRepository).findAll();
    }

    /**
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes_returnsOneQuiz_whenOnlyOneQuizIsFound() {
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
        when(mockQuizRepository.findAll()).thenReturn(quizList);
        List<QuizDto> actualAllQuizzes = mockQuizService.getAllQuizzes();
        assertEquals(1, actualAllQuizzes.size());
        QuizDto getResult = actualAllQuizzes.get(0);
        assertEquals(123L, getResult.getCreatorId());
        assertEquals(123L, getResult.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, getResult.getQuizCategory());
        assertEquals("Name", getResult.getName());
        verify(mockQuizRepository).findAll();
    }

    /**
     * Method under test: {@link QuizService#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes_throwsException_whenFindAllMethodThrowsException() {
        when(mockQuizRepository.findAll()).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.getAllQuizzes());
        verify(mockQuizRepository).findAll();
    }

    /**
     * Method under test: {@link QuizService#updateQuiz(long, QuizDto)}
     */
    @Test
    void testUpdateQuiz_updatesQuizInDatabaseWithGivenQuizDTO() {
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
        when(mockQuizRepository.save((Quiz) any())).thenReturn(quiz1);
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRole(Role.UNAUTHORISED_TRAINER);
        user2.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user2);

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        mockQuizService.updateQuiz(123L, quizDto);
        verify(mockQuizRepository).save((Quiz) any());
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#updateQuiz(long, QuizDto)}
     */
    @Test
    void testUpdateQuiz_throwsUserNotFoundException_whenUserCanNotBeFoundInDatabase() {
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
        when(mockQuizRepository.save((Quiz) any())).thenReturn(quiz1);
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockUserService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.updateQuiz(123L, quizDto));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#deleteQuizById(long)}
     */
    @Test
    void testDeleteQuizById_deletesQuizFromDatabase_whenQuizCanBeFoundInDatabase() {
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
        doNothing().when(mockQuizRepository).deleteById((Long) any());
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        mockQuizService.deleteQuizById(123L);
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQuizRepository).deleteById((Long) any());
        assertEquals(quizQuestionGradeList, mockQuizService.getAllQuizzes());
    }


    /**
     * Method under test: {@link QuizService#deleteQuizById(long)}
     */
    @Test
    void testDeleteQuizById_throwsQuizNotFoundException_whenQuizCanNotBeFoundInDatabase() {
        doNothing().when(mockQuizRepository).deleteById((Long) any());
        when(mockQuizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.deleteQuizById(123L));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizById(long)}
     */
    @Test
    void testGetQuizById_returnsQuiz_whenQuizCanBeFoundInDatabase() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(quiz, mockQuizService.getQuizById(123L));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizById(long)}
     */
    @Test
    void testGetQuizById_throwsQuizNotFoundException_whenQuizCanNotBeFoundInDatabase() {
        when(mockQuizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.getQuizById(123L));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#addQuestionIntoQuiz(Question, Quiz, Float)}
     */
    @Test
    void testAddQuestionIntoQuiz_addsQuestionToQuiz_whenQuestionAndQuizCanBeFoundInDatabase() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

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

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user2);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz1);
        when(mockQqgService.save((QuizQuestionGrade) any())).thenReturn(quizQuestionGrade);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user3);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());
        when(mockQuestionService.findById((Long) any())).thenReturn(question1);

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
        mockQuizService.addQuestionIntoQuiz(question2, quiz2, 10.0f);
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).save((QuizQuestionGrade) any());
        verify(mockQuestionService).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#addQuestionIntoQuiz(Question, Quiz, Float)}
     */
    @Test
    void testAddQuestionIntoQuiz_throwsException_whenQuizQuestionGradeCanNotBeSavedIntoDatabase() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.save((QuizQuestionGrade) any()))
                .thenThrow(new QuizNotFoundException("An error occurred"));

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
        when(mockQuestionService.findById((Long) any())).thenReturn(question);

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
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.addQuestionIntoQuiz(question1, quiz1, 10.0f));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).save((QuizQuestionGrade) any());
        verify(mockQuestionService).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#addQuestionIntoQuiz(Question, Quiz, Float)}
     */
    @Test
    void testAddQuestionIntoQuiz_throwsQuizNotFoundException_whenQuizCanNotBeFoundInDatabase() {
        when(mockQuizRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        when(mockQqgService.save((QuizQuestionGrade) any())).thenReturn(quizQuestionGrade);

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
        when(mockQuestionService.findById((Long) any())).thenReturn(question1);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user3);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user4);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.addQuestionIntoQuiz(question2, quiz1, 10.0f));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQuestionService).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#removeQuestionFromQuiz(Question, Quiz)}
     */
    @Test
    void testRemoveQuestionFromQuiz_removesQuestionFromQuiz_whenQuestionAndQuizCanBeFoundInDatabase() {
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
        doNothing().when(mockQqgService).remove((QuizQuestionGrade) any());
        when(mockQqgService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade);

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
        mockQuizService.removeQuestionFromQuiz(question1, quiz1);
        verify(mockQqgService).findById((QuizQuestionGradeKey) any());
        verify(mockQqgService).remove((QuizQuestionGrade) any());
    }

    /**
     * Method under test: {@link QuizService#removeQuestionFromQuiz(Question, Quiz)}
     */
    @Test
    void testRemoveQuestionFromQuiz_throwsException_whenQuestionGradeCanNotBeRemovedFromDatabase() {
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
        doThrow(new QuizNotFoundException("An error occurred")).when(mockQqgService)
                .remove((QuizQuestionGrade) any());
        when(mockQqgService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade);

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
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.removeQuestionFromQuiz(question1, quiz1));
        verify(mockQqgService).findById((QuizQuestionGradeKey) any());
        verify(mockQqgService).remove((QuizQuestionGrade) any());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId_returnsEmptyList_whenNoQuizIsFoundInDatabase() {
        when(mockQuizRepository.findByCreator((User) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user);
        assertTrue(mockQuizService.getQuizzesByCreatorId(123L).isEmpty());
        verify(mockQuizRepository).findByCreator((User) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId_throwsException_whenCreatorCanNotBeFoundInDatabase() {
        when(mockQuizRepository.findByCreator((User) any())).thenReturn(new ArrayList<>());
        when(mockUserService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.getQuizzesByCreatorId(123L));
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId_returnsOneQuiz_whenOneQuizIsFoundInDatabase() {
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
        when(mockQuizRepository.findByCreator((User) any())).thenReturn(quizList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRole(Role.UNAUTHORISED_TRAINER);
        user1.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user1);
        List<QuizDto> actualQuizzesByCreatorId = mockQuizService.getQuizzesByCreatorId(123L);
        assertEquals(1, actualQuizzesByCreatorId.size());
        QuizDto getResult = actualQuizzesByCreatorId.get(0);
        assertEquals(123L, getResult.getCreatorId());
        assertEquals(123L, getResult.getQuizId());
        assertEquals(QuizCategory.COURSE_QUIZ, getResult.getQuizCategory());
        assertEquals("Name", getResult.getName());
        verify(mockQuizRepository).findByCreator((User) any());
        verify(mockUserService).getUserById(anyLong());
    }


    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade_returns0_whenQuizCanBeFoundInDatabase_andQuizQuestionGradeIsEmptyList() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals(0.0f, mockQuizService.getMaxGrade(123L));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade_returnsMaxGradeOfQuizQuestionGradeList_whenQuizCanBeFoundInDatabase_andQuizQuestionGradeListIsNotEmpty() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals(10.0f, mockQuizService.getMaxGrade(123L));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#getMaxGrade(Long)}
     */
    @Test
    void testGetMaxGrade_throwsException_whenQuizCanNotBeFoundInDatabase() {
        when(mockQuizRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.getMaxGrade(123L));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory_throwsException_whenActiveUserIsUNAUTHORISED_TRAINER_andQuizCategoryIsCOURSE_QUIZ() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user);
        assertThrows(UserUnauthorisedError.class,
                () -> mockQuizService.checkAccessToQuizCategory(QuizCategory.COURSE_QUIZ, 123L));
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory_doNothing_ifActiveUserIsAUTHORISED_TRAINER_andQuizCategoryIsCOURSE_QUIZ() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.AUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user);
        mockQuizService.checkAccessToQuizCategory(QuizCategory.COURSE_QUIZ, 123L);
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory_throwsException_ifActiveUserIsUNAUTHORISED_TRAINER_andQuizCategoryIsINTERVIEW_QUIZ() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRole(Role.UNAUTHORISED_TRAINER);
        user.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(user);
        assertThrows(UserUnauthorisedError.class,
                () -> mockQuizService.checkAccessToQuizCategory(QuizCategory.INTERVIEW_QUIZ, 123L));
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizCategory(QuizCategory, long)}
     */
    @Test
    void testCheckAccessToQuizCategory_throwsException_ifActiveUserCanNotBeFoundInDatabase() {
        when(mockUserService.getUserById(anyLong())).thenThrow(new UserNotFoundException("An error occurred"));
        assertThrows(UserNotFoundException.class,
                () -> mockQuizService.checkAccessToQuizCategory(QuizCategory.COURSE_QUIZ, 123L));
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId_doNothing_whenActiveUserIsTheCreatorOfQuiz() {
        User creator = new User();
        creator.setEmail("jane.doe@example.org");
        creator.setFirstName("Jane");
        creator.setId(123L);
        creator.setLastName("Doe");
        creator.setPassword("iloveyou");
        creator.setRole(Role.UNAUTHORISED_TRAINER);
        creator.setUsername("janedoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(creator);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

        User activeUser = new User();
        activeUser.setEmail("jane.doe@example.org");
        activeUser.setFirstName("Jane");
        activeUser.setId(123L);
        activeUser.setLastName("Doe");
        activeUser.setPassword("iloveyou");
        activeUser.setRole(Role.UNAUTHORISED_TRAINER);
        activeUser.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(activeUser);
        mockQuizService.checkAccessToQuizId(123L, 123L);
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId_throwsException_whenActiveUserCanNotBeFoundInDatabase() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockUserService.getUserById(anyLong())).thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.checkAccessToQuizId(123L, 123L));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId_throwsException_whenQuizCanNotBeFoundInDatabase() {
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
        Optional<Quiz> ofResult = Optional.empty();
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockUserService.getUserById(anyLong())).thenReturn(user);
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.checkAccessToQuizId(123L, 123L));
        verify(mockQuizRepository).findById((Long) any());

    }

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId_doNothing_whenActiveUserIsNotCreatorOfQuiz_butIsAUTHORISED_TRAINER() {
        User creator = new User();
        creator.setEmail("alex.doe@example.org");
        creator.setFirstName("Alex");
        creator.setId(123L);
        creator.setLastName("Doe");
        creator.setPassword("iloveyou");
        creator.setRole(Role.UNAUTHORISED_TRAINER);
        creator.setUsername("alexdoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(creator);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

        User activeUser = new User();
        activeUser.setEmail("jane.doe@example.org");
        activeUser.setFirstName("Jane");
        activeUser.setId(1L);
        activeUser.setLastName("Doe");
        activeUser.setPassword("iloveyou");
        activeUser.setRole(Role.AUTHORISED_TRAINER);
        activeUser.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(activeUser);
        mockQuizService.checkAccessToQuizId(123L, 1L);
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }
    
    

    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId_doNothing_whenActiveUserIsNotCreatorOfQuiz_butIsAUTHORISED_SALES() {
        User creator = new User();
        creator.setEmail("alex.doe@example.org");
        creator.setFirstName("Alex");
        creator.setId(123L);
        creator.setLastName("Doe");
        creator.setPassword("iloveyou");
        creator.setRole(Role.UNAUTHORISED_TRAINER);
        creator.setUsername("alexdoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(creator);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

        User activeUser = new User();
        activeUser.setEmail("jane.doe@example.org");
        activeUser.setFirstName("Jane");
        activeUser.setId(1L);
        activeUser.setLastName("Doe");
        activeUser.setPassword("iloveyou");
        activeUser.setRole(Role.AUTHORISED_SALES);
        activeUser.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(activeUser);
        mockQuizService.checkAccessToQuizId(123L, 1L);
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }
    
    /**
     * Method under test: {@link QuizService#checkAccessToQuizId(long, long)}
     */
    @Test
    void testCheckAccessToQuizId_throwsException_whenActiveUserIsNotCreatorOfQuiz_andNotAUTHORISED_TRAINER_andNotAUTHORISED_SALES() {
        User creator = new User();
        creator.setEmail("alex.doe@example.org");
        creator.setFirstName("Alex");
        creator.setId(123L);
        creator.setLastName("Doe");
        creator.setPassword("iloveyou");
        creator.setRole(Role.UNAUTHORISED_TRAINER);
        creator.setUsername("alexdoe");

        Quiz quiz = new Quiz();
        quiz.setCreator(creator);
        quiz.setId(123L);
        quiz.setName("Name");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());
        Optional<Quiz> ofResult = Optional.of(quiz);
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

        User activeUser = new User();
        activeUser.setEmail("jane.doe@example.org");
        activeUser.setFirstName("Jane");
        activeUser.setId(1L);
        activeUser.setLastName("Doe");
        activeUser.setPassword("iloveyou");
        activeUser.setRole(Role.TRAINING);
        activeUser.setUsername("janedoe");
        when(mockUserService.getUserById(anyLong())).thenReturn(activeUser);
        assertThrows(UserUnauthorisedError.class, () -> mockQuizService.checkAccessToQuizId(123L, 1L));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockUserService).getUserById(anyLong());
    }

    /**
     * Method under test: {@link QuizService#checkQuestionTagMatchQuizCategory(QuizCategory, Question)}
     */
    @Test
    void testCheckQuestionTagMatchQuizCategory_doNothing_whenTagOfQuestionMatchesQuizCategory() {
        Tag tag = new Tag();
        tag.setId(123L);
        tag.setTagName("interview");
        tag.setQuestions(new HashSet<>());
        when(mockTagService.getTagByName((String) any())).thenReturn(tag);

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
        question.setTags(new HashSet<>(Arrays.asList(tag)));
        mockQuizService.checkQuestionTagMatchQuizCategory(QuizCategory.INTERVIEW_QUIZ, question);
        verify(mockTagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#checkQuestionTagMatchQuizCategory(QuizCategory, Question)}
     */
    @Test
    void testCheckQuestionTagMatchQuizCategory_throwsException_whenQuestionTagDoesNotMatchQuizCategory() {
        Tag tag = new Tag();
        tag.setId(123L);
        tag.setTagName("course");
        tag.setQuestions(new HashSet<>());
        when(mockTagService.getTagByName((String) any())).thenReturn(tag);

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
                () -> mockQuizService.checkQuestionTagMatchQuizCategory(QuizCategory.COURSE_QUIZ, question));
        verify(mockTagService).getTagByName((String) any());
    }


    /**
     * Method under test: {@link QuizService#checkQuestionTagMatchQuizCategory(QuizCategory, Question)}
     */
    @Test
    void testCheckQuestionTagMatchQuizCategory_throwsException_whenTagIsNotFoundInDatabase() {
        when(mockTagService.getTagByName((String) any())).thenThrow(new QuizNotFoundException("An error occurred"));

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
                () -> mockQuizService.checkQuestionTagMatchQuizCategory(QuizCategory.COURSE_QUIZ, question));
        verify(mockTagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions_whenQuestionListIsEmpty() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());
        mockQuizService.createQuizQuestions(1L, new ArrayList<>());
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions_throwsException_whenQuesitonGradeCanNotBeFound() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.findAllByQuizId(anyLong()))
                .thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.createQuizQuestions(1L, new ArrayList<>()));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions_throwsException_whenQuizCanNotBeFound() {
        when(mockQuizRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.createQuizQuestions(1L, new ArrayList<>()));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions_throwsException_whenQuizAlreadyExistInDatabase() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

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

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user2);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz1);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);
        assertThrows(QuizAlreadyExistsException.class, () -> mockQuizService.createQuizQuestions(1L, new ArrayList<>()));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
        
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions_throwsException_whenQuesitonTagDoesNotMatchQuizCategory() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());

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
        when(mockQuestionService.findById((Long) any())).thenReturn(question);

        Tag tag = new Tag();
        tag.setId(123L);
        tag.setTagName("Tag Name");
        tag.setQuestions(new HashSet<>());
        when(mockTagService.getTagByName((String) any())).thenReturn(tag);

        QuestionGradeDTO questionGradeDTO = new QuestionGradeDTO();
        questionGradeDTO.setGrade(10.0f);
        questionGradeDTO.setQuestionDetails("Question Details");
        questionGradeDTO.setQuestionId(123L);
        questionGradeDTO.setTags(new HashSet<>());

        ArrayList<QuestionGradeDTO> questionGradeDTOList = new ArrayList<>();
        questionGradeDTOList.add(questionGradeDTO);
        assertThrows(QuestionTagNotMatchQuizCategory.class,
                () -> mockQuizService.createQuizQuestions(1L, questionGradeDTOList));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
        verify(mockQuestionService).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#createQuizQuestions(long, List)}
     */
    @Test
    void testCreateQuizQuestions_throwsException_whenTagCanNotBeFound() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());

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
        when(mockQuestionService.findById((Long) any())).thenReturn(question);
        when(mockTagService.getTagByName((String) any())).thenThrow(new QuizNotFoundException("An error occurred"));

        QuestionGradeDTO questionGradeDTO = new QuestionGradeDTO();
        questionGradeDTO.setGrade(10.0f);
        questionGradeDTO.setQuestionDetails("Question Details");
        questionGradeDTO.setQuestionId(123L);
        questionGradeDTO.setTags(new HashSet<>());

        ArrayList<QuestionGradeDTO> questionGradeDTOList = new ArrayList<>();
        questionGradeDTOList.add(questionGradeDTO);
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.createQuizQuestions(1L, questionGradeDTOList));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
        verify(mockQuestionService).findById((Long) any());
        verify(mockTagService).getTagByName((String) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions_updatesQuizQuetions_whenGivenQuizAndQuizQuestionGradeCanBeFound() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());
        mockQuizService.updateQuizQuestions(1L, new ArrayList<>());
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions_throwsException_whenQuizQuestionGradeCanNotBeFound() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);
        when(mockQqgService.findAllByQuizId(anyLong()))
                .thenThrow(new QuizNotFoundException("An error occurred"));
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.updateQuizQuestions(1L, new ArrayList<>()));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions_throwsException_whenQuizCanNotBeFound() {
        when(mockQuizRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.updateQuizQuestions(1L, new ArrayList<>()));
        verify(mockQuizRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions_updatesQuestionListOfTheQuiz() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

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

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user2);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz1);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);

        QuizQuestionGradeKey quizQuestionGradeKey1 = new QuizQuestionGradeKey();
        quizQuestionGradeKey1.setQuestionId(123L);
        quizQuestionGradeKey1.setQuizId(123L);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user3);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Quiz quiz2 = new Quiz();
        quiz2.setCreator(user4);
        quiz2.setId(123L);
        quiz2.setName("Name");
        quiz2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz2.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade1 = new QuizQuestionGrade();
        quizQuestionGrade1.setGrade(10.0f);
        quizQuestionGrade1.setKey(quizQuestionGradeKey1);
        quizQuestionGrade1.setQuestion(question1);
        quizQuestionGrade1.setQuiz(quiz2);
        doNothing().when(mockQqgService).remove((QuizQuestionGrade) any());
        when(mockQqgService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade1);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRole(Role.UNAUTHORISED_TRAINER);
        user5.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user5);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());
        when(mockQuestionService.findById((Long) any())).thenReturn(question2);
        mockQuizService.updateQuizQuestions(1L, new ArrayList<>());
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findById((QuizQuestionGradeKey) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
        verify(mockQqgService).remove((QuizQuestionGrade) any());
        verify(mockQuestionService).findById((Long) any());
    }

    /**
     * Method under test: {@link QuizService#updateQuizQuestions(long, List)}
     */
    @Test
    void testUpdateQuizQuestions_throwsException_whenQuesitonCanNotBeRemoveFromQuiz() {
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
        when(mockQuizRepository.findById((Long) any())).thenReturn(ofResult);

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

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(user2);
        quiz1.setId(123L);
        quiz1.setName("Name");
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz1.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question);
        quizQuestionGrade.setQuiz(quiz1);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);

        QuizQuestionGradeKey quizQuestionGradeKey1 = new QuizQuestionGradeKey();
        quizQuestionGradeKey1.setQuestionId(123L);
        quizQuestionGradeKey1.setQuizId(123L);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRole(Role.UNAUTHORISED_TRAINER);
        user3.setUsername("janedoe");

        Question question1 = new Question();
        question1.setCreator(user3);
        question1.setId(123L);
        question1.setQuestionDetails("Question Details");
        question1.setTags(new HashSet<>());

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRole(Role.UNAUTHORISED_TRAINER);
        user4.setUsername("janedoe");

        Quiz quiz2 = new Quiz();
        quiz2.setCreator(user4);
        quiz2.setId(123L);
        quiz2.setName("Name");
        quiz2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz2.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade1 = new QuizQuestionGrade();
        quizQuestionGrade1.setGrade(10.0f);
        quizQuestionGrade1.setKey(quizQuestionGradeKey1);
        quizQuestionGrade1.setQuestion(question1);
        quizQuestionGrade1.setQuiz(quiz2);
        doThrow(new QuizNotFoundException("An error occurred")).when(mockQqgService)
                .remove((QuizQuestionGrade) any());
        when(mockQqgService.findById((QuizQuestionGradeKey) any())).thenReturn(quizQuestionGrade1);
        when(mockQqgService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRole(Role.UNAUTHORISED_TRAINER);
        user5.setUsername("janedoe");

        Question question2 = new Question();
        question2.setCreator(user5);
        question2.setId(123L);
        question2.setQuestionDetails("Question Details");
        question2.setTags(new HashSet<>());
        when(mockQuestionService.findById((Long) any())).thenReturn(question2);
        assertThrows(QuizNotFoundException.class, () -> mockQuizService.updateQuizQuestions(1L, new ArrayList<>()));
        verify(mockQuizRepository).findById((Long) any());
        verify(mockQqgService).findById((QuizQuestionGradeKey) any());
        verify(mockQqgService).findAllByQuizId(anyLong());
        verify(mockQqgService).remove((QuizQuestionGrade) any());
        verify(mockQuestionService).findById((Long) any());
    }

}

