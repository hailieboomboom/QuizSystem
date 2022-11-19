package com.fdmgroup.QuizSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;

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

@ContextConfiguration(classes = {QuizQuestionGradeService.class})
@ExtendWith(SpringExtension.class)
class QuizQuestionGradeServiceTest {
    @MockBean
    private QuizQuestionGradeRepository quizQuestionGradeRepository;

    @Autowired
    private QuizQuestionGradeService quizQuestionGradeService;

    /**
     * Method under test: {@link QuizQuestionGradeService#save(QuizQuestionGrade)}
     */
    @Test
    void testSave_returnsQuizQuestionGrade() {
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
        when(quizQuestionGradeRepository.save(any())).thenReturn(quizQuestionGrade);

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
        assertSame(quizQuestionGrade, quizQuestionGradeService.save(quizQuestionGrade1));
        verify(quizQuestionGradeRepository).save((QuizQuestionGrade) any());
    }

    /**
     * Method under test: {@link QuizQuestionGradeService#findById(QuizQuestionGradeKey)}
     */
    @Test
    void testFindById_returnsListOfQuizQuestionGrade_whenQuizCanBeFoundByGivenQuizId() {
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
        Optional<QuizQuestionGrade> ofResult = Optional.of(quizQuestionGrade);
        when(quizQuestionGradeRepository.findByKey((QuizQuestionGradeKey) any())).thenReturn(ofResult);

        QuizQuestionGradeKey quizQuestionGradeKey1 = new QuizQuestionGradeKey();
        quizQuestionGradeKey1.setQuestionId(123L);
        quizQuestionGradeKey1.setQuizId(123L);
        assertSame(quizQuestionGrade, quizQuestionGradeService.findById(quizQuestionGradeKey1));
        verify(quizQuestionGradeRepository).findByKey((QuizQuestionGradeKey) any());
    }

    /**
     * Method under test: {@link QuizQuestionGradeService#findById(QuizQuestionGradeKey)}
     */
    @Test
    void testFindById_returnsNull_whenQuizCanNotBeFoundByGivenQuizId() {
        when(quizQuestionGradeRepository.findByKey((QuizQuestionGradeKey) any())).thenReturn(Optional.empty());

        QuizQuestionGradeKey quizQuestionGradeKey = new QuizQuestionGradeKey();
        quizQuestionGradeKey.setQuestionId(123L);
        quizQuestionGradeKey.setQuizId(123L);
        assertNull(quizQuestionGradeService.findById(quizQuestionGradeKey));
        verify(quizQuestionGradeRepository).findByKey((QuizQuestionGradeKey) any());
    }

    /**
     * Method under test: {@link QuizQuestionGradeService#findAllByQuizId(long)}
     */
    @Test
    void testFindAllByQuizId_returnsListOfQuizQuestionGrade() {
        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        when(quizQuestionGradeRepository.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);
        List<QuizQuestionGrade> actualFindAllByQuizIdResult = quizQuestionGradeService.findAllByQuizId(123L);
        assertSame(quizQuestionGradeList, actualFindAllByQuizIdResult);
        assertTrue(actualFindAllByQuizIdResult.isEmpty());
        verify(quizQuestionGradeRepository).findAllByQuizId(anyLong());
    }

    /**
     * Method under test: {@link QuizQuestionGradeService#remove(QuizQuestionGrade)}
     */
    @Test
    void testRemove_removesGivenQuizQuestionGrade() {
        doNothing().when(quizQuestionGradeRepository).delete((QuizQuestionGrade) any());

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
        quizQuestionGradeService.remove(quizQuestionGrade);
        verify(quizQuestionGradeRepository).delete((QuizQuestionGrade) any());
        assertEquals(10.0f, quizQuestionGrade.getGrade());
        assertSame(quiz, quizQuestionGrade.getQuiz());
        assertSame(question, quizQuestionGrade.getQuestion());
        assertSame(quizQuestionGradeKey, quizQuestionGrade.getKey());
    }
}

