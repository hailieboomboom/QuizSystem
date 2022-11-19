package com.fdmgroup.QuizSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {QuizController.class})
@ExtendWith(SpringExtension.class)
class QuizControllerTest {
    @MockBean
    private ModelToDTO modelToDTO;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private QuizController quizController;

    @MockBean
    private QuizQuestionGradeService quizQuestionGradeService;

    @MockBean
    private QuizService quizService;

    /**
     * Method under test: {@link QuizController#createQuiz(long, QuizDto)}
     */
    @Test
    void testCreateQuiz_returnsResponseEntity_thatContainsQuizDtoAndCreatedHttpStatus() throws Exception {
        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        when(quizService.createQuiz((QuizDto) any())).thenReturn(quizDto);
        doNothing().when(quizService).checkAccessToQuizCategory((QuizCategory) any(), anyLong());

        QuizDto quizDto1 = new QuizDto();
        quizDto1.setCreatorId(123L);
        quizDto1.setName("Name");
        quizDto1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto1.setQuizId(123L);
        String content = (new ObjectMapper()).writeValueAsString(quizDto1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quizzes/{activeUserId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(quizController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"quizId\":123,\"creatorId\":123,\"name\":\"Name\",\"quizCategory\":\"COURSE_QUIZ\"}"));

        InOrder order = inOrder(quizService);
        order.verify(quizService,times(1)).checkAccessToQuizCategory((QuizCategory) any(), anyLong());
        order.verify(quizService, times(1)).createQuiz((QuizDto) any());
    }

    /**
     * Method under test: {@link QuizController#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes_buildsRequest_and_returnsResponseEntity_thatContainsListOfQuizDtoAndOkHttpStatus() throws Exception {
        when(quizService.getAllQuizzes()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quizzes");
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));

        verify(quizService,times(1)).getAllQuizzes();
    }

    /**
     * Method under test: {@link QuizController#getAllQuizzes()}
     */
    @Test
    void testGetAllQuizzes_returnsResponseEntity_thatContainsListOfQuizDtoAndOkHttpStatus() throws Exception {
        when(quizService.getAllQuizzes()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/quizzes");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));

        verify(quizService,times(1)).getAllQuizzes();
    }

    /**
     * Method under test: {@link QuizController#getQuizById(long)}
     */
    @Test
    void testGetQuizById_returnsResponseEntity_thatContainsQuizDtoAndOkHttpStatus() throws Exception {
        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        when(modelToDTO.quizToOutput((Quiz) any())).thenReturn(quizDto);

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
        when(quizService.getQuizById(anyLong())).thenReturn(quiz);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quizzes/{id}", 123L);
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"quizId\":123,\"creatorId\":123,\"name\":\"Name\",\"quizCategory\":\"COURSE_QUIZ\"}"));

        InOrder order = inOrder(quizService, modelToDTO);
        order.verify(quizService,times(1)).getQuizById(anyLong());
        order.verify(modelToDTO,times(1)).quizToOutput((Quiz) any());
    }

    /**
     * Method under test: {@link QuizController#updateQuiz(long, long, QuizDto)}
     */
    @Test
    void testUpdateQuiz_returnsResponseEntity_thatContainsApiResponseOfSuccessMessage() throws Exception {
        doNothing().when(quizService).checkAccessToQuizCategory((QuizCategory) any(), anyLong());
        doNothing().when(quizService).checkAccessToQuizId(anyLong(), anyLong());
        doNothing().when(quizService).updateQuiz(anyLong(), (QuizDto) any());

        QuizDto quizDto = new QuizDto();
        quizDto.setCreatorId(123L);
        quizDto.setName("Name");
        quizDto.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizDto.setQuizId(123L);
        String content = (new ObjectMapper()).writeValueAsString(quizDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/quizzes/{id}/{activeUserId}", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":true,\"message\":\"Product has been updated\"}"));

        InOrder order = inOrder(quizService);
        order.verify(quizService, times(1)).checkAccessToQuizId(anyLong(), anyLong());
        order.verify(quizService, times(1)).checkAccessToQuizCategory((QuizCategory) any(), anyLong());
        order.verify(quizService, times(1)).updateQuiz(anyLong(), (QuizDto) any());
    }

    /**
     * Method under test: {@link QuizController#createQuizQuestions(long, long, List)}
     */
    @Test
    void testCreateQuizQuestions_returnsResponseEntity_thatContainsApiResponseOfSuccessMessage() throws Exception {
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
        doNothing().when(quizService).checkAccessToQuizCategory((QuizCategory) any(), anyLong());
        doNothing().when(quizService).checkAccessToQuizId(anyLong(), anyLong());
        doNothing().when(quizService).createQuizQuestions(anyLong(), (List<QuestionGradeDTO>) any());
        when(quizService.getQuizById(anyLong())).thenReturn(quiz);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/api/quizzes/{id}/questions/{activeUserId}", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"success\":true,\"message\":\"Successfully update questions to quiz\"}"));
    }

    /**
     * Method under test: {@link QuizController#updateQuizQuestions(long, long, List)}
     */
    @Test
    void testUpdateQuizQuestions_returnsResponseEntity_thatContainsApiResponseOfSuccessMessage() throws Exception {
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
        doNothing().when(quizService).checkAccessToQuizCategory((QuizCategory) any(), anyLong());
        doNothing().when(quizService).checkAccessToQuizId(anyLong(), anyLong());
        doNothing().when(quizService).updateQuizQuestions(anyLong(), (List<QuestionGradeDTO>) any());
        when(quizService.getQuizById(anyLong())).thenReturn(quiz);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .put("/api/quizzes/{id}/questions/{activeUserId}", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"success\":true,\"message\":\"Successfully update questions to quiz\"}"));
    }

    /**
     * Method under test: {@link QuizController#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId_buildsRequest_returnsResponseEntity_thatContainsListOfQuizDTOs_andOkHttpStatus() throws Exception {
        when(quizService.getQuizzesByCreatorId(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quizzes/users/{id}", 123L);
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link QuizController#getQuizzesByCreatorId(long)}
     */
    @Test
    void testGetQuizzesByCreatorId_returnsResponseEntity_thatContainsListOfQuizDTOs_andOkHttpStatus() throws Exception {
        when(quizService.getQuizzesByCreatorId(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/quizzes/users/{id}", 123L);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link QuizController#deleteQuiz(long, long)}
     */
    @Test
    void testDeleteQuiz_returnsResponseEntity_thatContainsApiResponseOfSuccessMessage() throws Exception {
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
        doNothing().when(quizService).checkAccessToQuizCategory((QuizCategory) any(), anyLong());
        doNothing().when(quizService).checkAccessToQuizId(anyLong(), anyLong());
        doNothing().when(quizService).deleteQuizById(anyLong());
        when(quizService.getQuizById(anyLong())).thenReturn(quiz);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/quizzes/{id}/{activeUserId}",
                123L, 123L);
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":true,\"message\":\"Product has been deleted\"}"));
    }

    /**
     * Method under test: {@link QuizController#getAllQuestionsByQuizId(long)}
     */
    @Test
    void testGetAllQuestionsByQuizId_buildsRequest_returnsResponseEntity_thatContainsListOfQuestionGradeDTO_andOkHttpStatus() throws Exception {
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quizzes/{id}/questions", 123L);
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link QuizController#getAllQuestionsByQuizId(long)}
     */
    @Test
    void testGetAllQuestionsByQuizId_returnsResponseEntity_thatContainsListOfQuizDTOs_andOkHttpStatus() throws Exception {
        QuestionGradeDTO questionGradeDTO = new QuestionGradeDTO();
        questionGradeDTO.setGrade(10.0f);
        questionGradeDTO.setQuestionDetails("Question Details");
        questionGradeDTO.setQuestionId(123L);
        questionGradeDTO.setTags(new HashSet<>());
        when(modelToDTO.qqgToQg((QuizQuestionGrade) any())).thenReturn(questionGradeDTO);

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

        Question question1 = new Question();
        question1.setCreator(user1);
        question1.setId(123L);
        question1.setQuestionDetails("?");
        question1.setTags(new HashSet<>());

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
        quiz.setName("?");
        quiz.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quiz.setQuizQuestionsGrade(new ArrayList<>());

        QuizQuestionGrade quizQuestionGrade = new QuizQuestionGrade();
        quizQuestionGrade.setGrade(10.0f);
        quizQuestionGrade.setKey(quizQuestionGradeKey);
        quizQuestionGrade.setQuestion(question1);
        quizQuestionGrade.setQuiz(quiz);

        ArrayList<QuizQuestionGrade> quizQuestionGradeList = new ArrayList<>();
        quizQuestionGradeList.add(quizQuestionGrade);
        when(quizQuestionGradeService.findAllByQuizId(anyLong())).thenReturn(quizQuestionGradeList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quizzes/{id}/questions", 123L);
        MockMvcBuilders.standaloneSetup(quizController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"questionId\":123,\"questionDetails\":\"Question Details\",\"grade\":10.0,\"tags\":[]}]"));
    }
}

