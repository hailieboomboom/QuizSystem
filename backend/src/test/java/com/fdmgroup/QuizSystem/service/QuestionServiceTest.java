package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.dto.McqDto.AddMcqDto;
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.ReturnMcqDto;
import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.NotEnoughAccessException;
import com.fdmgroup.QuizSystem.exception.McqException.TagNotValidException;
import com.fdmgroup.QuizSystem.model.*;
import com.fdmgroup.QuizSystem.repository.McqRepository;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuizAttemptService mockQuizAttemptService;
    @Mock
    private QuizQuestionMCQAttemptRepository mockQuizQuestionMCQAttemptRepository;
    @Mock
    private QuestionRepository mockQuestionRepository;
    @Mock
    private McqRepository mockMcqRepository;
    @Mock
    private TagService mockTagService;
    @Mock
    private MultipleChoiceOptionService mockMultipleChoiceOptionService;
    @Mock
    private UserService mockUserService;
    @Mock
    private QuizQuestionGradeRepository mockQqgRepository;

    @Mock
    private MultipleChoiceQuestion mockMcq;

    @Mock
    private AddMcqDto mockAddMcqDto;

    @Spy
    private QuestionService questionService;
    private User user;
    private Question question;
    private Tag tag;
    private Tag tagCourse;
    private  MultipleChoiceQuestion mcq;
    private MultipleChoiceOption option1;
    private MultipleChoiceOption option2;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(mockQuizAttemptService, mockQuizQuestionMCQAttemptRepository,
                mockQuestionRepository, mockMcqRepository, mockTagService, mockMultipleChoiceOptionService,
                mockUserService, mockQqgRepository);
        user = new User(0L, "chris", "12345", "123@gmail.com", "chris", "tang", Role.TRAINING);
        tag = new Tag("interview");
        Tag tagCourse = new Tag("course");
        question = new Question(0L, "questionDetail", user, Set.of(tag));
        mcq = new MultipleChoiceQuestion();
        mcq.setId(0L);
        mcq.setQuestionDetails("questionDetail");
        mcq.setCreator(user);
        mcq.setTags(Set.of(tag));
        option1 = new MultipleChoiceOption();
        option1.setId(0L);
        option1.setOptionDetail("option1");
        option1.setCorrect(false);
        option2 = new MultipleChoiceOption();
        option2.setId(1L);
        option2.setOptionDetail("option2");
        option2.setCorrect(true);
        mcq.setMcoptions(List.of(option1,option2));
    }

    @Test
    void test_Save_question() {


        // Configure QuestionRepository.save(...).

        when(mockQuestionRepository.save(question)).thenReturn(question);

        // Run the test
        final Question result = questionService.save(question);

        // Verify the results
        assertThat(result).isEqualTo(question);
    }

    @Test
    void test_FindAllQuestions() {
        // Setup
        final List<Question> questions = List.of(question);


        when(mockQuestionRepository.findAll()).thenReturn(questions);

        // Run the test
        final List<Question> result = questionService.findAllQuestions();

        // Verify the results
        assertThat(result).isEqualTo(questions);
    }

    @Test
    void test_FindAllQuestions_QuestionRepository_ReturnsNull() {
        // Setup
        when(mockQuestionRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Question> result = questionService.findAllQuestions();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void test_FindById_returns_question() {
        // Setup
        final Question expectedResult = question;

        // Configure QuestionRepository.findById(...).
        final Optional<Question> question = Optional.of(expectedResult);
        when(mockQuestionRepository.findById(0L)).thenReturn(question);

        // Run the test
        final Question result = questionService.findById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void test_FindById_QuestionRepository_Returns_Null() {
        // Setup
        when(mockQuestionRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Question result = questionService.findById(0L);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void test_Remove_isCalled_Once() {
        // Setup

        // Run the test
        questionService.remove(question);

        // Verify the results
        verify(mockQuestionRepository).delete(question);
    }

    @Test
    void test_FindQuestionsByCreator() {
        // Setup
//
        final List<Question> expectedResult = List.of(question);

        // Configure QuestionRepository.findByCreator(...).
        final List<Question> questions = List.of(question);
        when(mockQuestionRepository.findByCreator(any(User.class))).thenReturn(questions);

        // Run the test
        final List<Question> result = questionService.findQuestionsByCreator(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void test_CreateMcq(){

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        Mockito.doReturn(mcq).when(questionServiceSpy).save(any(MultipleChoiceQuestion.class));

        questionServiceSpy.createMCQ(mockAddMcqDto,user);
        verify(questionServiceSpy,times(2)).save(any(MultipleChoiceQuestion.class));
        verify(mockMultipleChoiceOptionService).createListOfOption(mockAddMcqDto.getOptions(), mcq);
    }

    @Test
    void test_updateMcq(){
        QuestionService questionServiceSpy = Mockito.spy(questionService);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(0L);
        Mockito.doReturn(mcq).when(questionServiceSpy).save(any(MultipleChoiceQuestion.class));
        Mockito.doReturn(1L).when(questionServiceSpy).findCreatorByIdOfAQuestion(0L);
        when(mockUserService.getUserById(1L)).thenReturn(user);
        questionServiceSpy.updateMCQ(mockAddMcqDto,0L);
        InOrder inOrder = inOrder(mockMultipleChoiceOptionService,mockTagService);
        inOrder.verify(mockMultipleChoiceOptionService).updateMcqOption(mockAddMcqDto.getOptions(), mcq);
        inOrder.verify(mockTagService).getTagsFromDto(mockAddMcqDto.getTags());

    }

    @Test
    void test_trainer_updateMcq(){
        Trainer trainer = new Trainer();
        trainer.setRole(Role.AUTHORISED_TRAINER);
        mcq.setTags(Set.of(new Tag("course")));
        Long questionId = 0L;
        Long activeUserId = 1L;

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(trainer);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        Mockito.doNothing().when(questionServiceSpy).updateMCQ(mockAddMcqDto,questionId);
        questionServiceSpy.updateMCQByRole(mockAddMcqDto,questionId,activeUserId);
        verify(questionServiceSpy).updateMCQ(mockAddMcqDto,questionId);
    }

    @Test
    void test_updateMcq_NotBelongsToTheUser_returnsErrorMessage(){
        Student student = new Student();
        Long questionId = 0L;
        Long activeUserId = 1L;

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(student);
        Mockito.doReturn(2L).when(questionServiceSpy).findCreatorByIdOfAQuestion(questionId);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        assertThatThrownBy(() -> questionServiceSpy.updateMCQByRole(mockAddMcqDto,questionId,activeUserId)).isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_Student_updateSuccess(){
        Student student = new Student();
        Long questionId = 0L;
        Long activeUserId = 1L;

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(student);
        Mockito.doReturn(1L).when(questionServiceSpy).findCreatorByIdOfAQuestion(questionId);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        Mockito.doNothing().when(questionServiceSpy).updateMCQ(mockAddMcqDto,questionId);
        questionServiceSpy.updateMCQByRole(mockAddMcqDto,questionId,activeUserId);
    }

    @Test
    void test_Sales_updateInterviewMcq_returnsErrorMessage(){
        Sales sales = new Sales();
        sales.setRole(Role.AUTHORISED_SALES);
        mcq.setTags(Set.of(new Tag("course")));
        Long questionId = 0L;
        Long activeUserId = 1L;


        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(sales);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        assertThatThrownBy(() -> questionServiceSpy.updateMCQByRole(mockAddMcqDto,questionId,activeUserId)).isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_Sales_updateInterviewMcq_Success(){
        Sales sales = new Sales();
        sales.setRole(Role.AUTHORISED_SALES);
        mcq.setTags(Set.of(new Tag("interview")));
        mcq.setId(0L);
        Long questionId = 0L;
        Long activeUserId = 1L;


        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(sales);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        Mockito.doNothing().when(questionServiceSpy).updateMCQ(mockAddMcqDto,questionId);
        questionServiceSpy.updateMCQByRole(mockAddMcqDto,questionId,activeUserId);
        verify(questionServiceSpy).updateMCQ(mockAddMcqDto,questionId);
    }

    @Test
    void test_getAllMcqQuestionForQuizCreation(){

        when(mockMcqRepository.findAll()).thenReturn(List.of(mockMcq,mcq));

        List<QuestionGradeDTO> list =questionService.getAllMcqQuestionforQuizCreation();
        assertEquals(2,list.size());


    }

    @Test
    void test_getMcqDtosforQuizEdit(){
        Long quizId = 1L;

        QuizQuestionGrade grade1 = new QuizQuestionGrade();
        QuizQuestionGrade grade2 = new QuizQuestionGrade();
        when(mockMcqRepository.findAll()).thenReturn(List.of(mockMcq,mcq));
        questionService.getMcqDtosforQuizEdit(quizId);
        verify(mockMcqRepository).findAll();
        verify(mockQqgRepository).findAllByQuizId(quizId);
    }


    @Test
    void test_getAllMcqQuestion(){
        when(mockMcqRepository.findAll()).thenReturn(List.of(mockMcq,mcq));
        List<ReturnMcqDto> returnMcqDtoList = questionService.getAllMcqQuestion();
        assertEquals(2,returnMcqDtoList.size());
        verify(mockMcqRepository).findAll();
    }

    @Test
    void test_getAllMcqCreatedByAUser(){

        Long userId =1L;
        user.setId(userId);
        when(mockUserService.getUserById(userId)).thenReturn(user);
        when(mockQuestionRepository.findAllByCreatorId(userId)).thenReturn(List.of(mockMcq,mcq));
        List<ReturnMcqDto> returnMcqDtoList = questionService.getAllMcqCreatedByAnUser(userId);
        assertEquals(0,returnMcqDtoList.size());
        verify(mockQuestionRepository).findAllByCreatorId(userId);
    }
    @Test
    void test_FindQuestionsByCreator_QuestionRepositoryReturns_empty() {
        // Setup

        when(mockQuestionRepository.findByCreator(any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Question> result = questionService.findQuestionsByCreator(user);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void test_getReturnMcDto(){

        ReturnMcqDto returnMcqDto = questionService.getReturnMcqDto(question);
        verify(mockMultipleChoiceOptionService).listOptionsForMcq(question.getId());
    }

    @Test
    void test_getMcqDto(){
        Long questionId = 0L;
        Long userId = 0L;
        QuestionService questionServiceSpy = Mockito.spy(questionService);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        Mockito.doReturn(userId).when(questionServiceSpy).findCreatorByIdOfAQuestion(questionId);
        when(mockMultipleChoiceOptionService.listOptionsForMcq(questionId)).thenReturn(List.of(new McqOptionDto(),new McqOptionDto()));
        questionServiceSpy.getMcqDto(questionId);


    }

    @Test
    void test_getMcqBank_thatNotExists_returnsError(){
        assertThatThrownBy(() -> questionService.getMcqBank("abc")).
                isInstanceOf(TagNotValidException.class);
    }

    @Test
    void test_getMcqBank_returnListOfMcqDto(){

        mcq.setTags(Set.of(new Tag("interview")));
        when(mockMcqRepository.findAll()).thenReturn(List.of(mcq));
        List<ReturnMcqDto> mcqDtoList = questionService.getMcqBank("interview");
        assertEquals(1,mcqDtoList.size());
    }

    @Test
    void test_trainer_deleteCourseMcq_Success(){
        Trainer trainer = new Trainer();
        trainer.setRole(Role.AUTHORISED_TRAINER);
        Long questionId = 0L;
        Long activeUserId = 1L;
        mcq.setTags(Set.of(new Tag("course")));

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(trainer);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        questionServiceSpy.deleteOneMcqByRole(questionId,activeUserId);
        verify(questionServiceSpy).deleteOneMcq(questionId);
    }



    @Test
    void test_DeleteMcq_NotBelongsToTheUser_returnsErrorMessage(){
        Student student = new Student();
        Long questionId = 0L;
        Long activeUserId = 1L;

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(student);
        Mockito.doReturn(2L).when(questionServiceSpy).findCreatorByIdOfAQuestion(questionId);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        assertThatThrownBy(() -> questionServiceSpy.deleteOneMcqByRole(questionId,activeUserId)).isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_Student_DeleteMcq_createdByThemselves_success(){
        Student student = new Student();
        Long questionId = 0L;
        Long activeUserId = 1L;

        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(student);
        Mockito.doReturn(1L).when(questionServiceSpy).findCreatorByIdOfAQuestion(questionId);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        questionServiceSpy.deleteOneMcqByRole(questionId,activeUserId);

    }

    @Test
    void test_Sales_deleteCourseMcq_returnsErrorMessage(){
        Sales sales = new Sales();
        sales.setRole(Role.AUTHORISED_SALES);
        mcq.setTags(Set.of(new Tag("course")));
        Long questionId = 0L;
        Long activeUserId = 1L;


        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(sales);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        assertThatThrownBy(() -> questionServiceSpy.deleteOneMcqByRole(questionId,activeUserId)).isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_Sales_deleteInterviewMcq_Success() {
        Sales sales = new Sales();
        sales.setRole(Role.AUTHORISED_SALES);
        mcq.setTags(Set.of(new Tag("interview")));
        Long questionId = 0L;
        Long activeUserId = 1L;


        QuestionService questionServiceSpy = Mockito.spy(questionService);
        when(mockUserService.getUserById(activeUserId)).thenReturn(sales);
        Mockito.doReturn(mcq).when(questionServiceSpy).findMcqById(questionId);
        questionServiceSpy.deleteOneMcqByRole(questionId, activeUserId);
        verify(questionServiceSpy).deleteOneMcq(questionId);
    }



    @Test
    void test_accessControlCreateMCQ_whenTagIsEmpty(){
        assertThatThrownBy(() -> questionService.accessControlCreateMCQ(null, Role.AUTHORISED_TRAINER)).
                isInstanceOf(TagNotValidException.class);
    }

    @Test
    void test_accessControlCreateMCQ_whenStudentInTraining_CreateInterviewQuestion(){
        assertThatThrownBy(() -> questionService.accessControlCreateMCQ(List.of("interview"), Role.TRAINING)).isInstanceOf(NotEnoughAccessException.class);
    }

    @Test
    void test_accessControlCreateMCQ_whenSales_CreateCourseContent(){
        assertThatThrownBy(() -> questionService.accessControlCreateMCQ(List.of("course"), Role.AUTHORISED_SALES)).isInstanceOf(NotEnoughAccessException.class);
    }




    @Test
    void test_UpdateMCQ_McqRepository_ReturnsNull() {
        // Setup
        final AddMcqDto addMcqDto = new AddMcqDto("questionDetail", 0L,
                List.of(new McqOptionDto(0L, "optionDescription", false)), List.of("value"));
        when(mockMcqRepository.findById(0L)).thenReturn(Optional.empty());
        // Run the test
        assertThatThrownBy(() -> questionService.updateMCQ(addMcqDto, 0L))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void testUpdateMCQ_QuestionRepositoryFindByIdReturns_Null() {
        // Setup
        final AddMcqDto addMcqDto = new AddMcqDto("questionDetail", 0L,
                List.of(new McqOptionDto(0L, "optionDescription", false)), List.of("interview"));
        final Optional<MultipleChoiceQuestion> multipleChoiceQuestion = Optional.of(mcq);
        when(mockMcqRepository.findById(0L)).thenReturn(multipleChoiceQuestion);

        when(mockQuestionRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> questionService.updateMCQ(addMcqDto, 0L))
                .isInstanceOf(NoDataFoundException.class);
    }
//
//

    @Test
    void test_FindMcqById() {
        // Setup

        final Optional<MultipleChoiceQuestion> multipleChoiceQuestion = Optional.of(mcq);
        when(mockMcqRepository.findById(0L)).thenReturn(multipleChoiceQuestion);

        // Configure QuestionRepository.findById(...).
        final Optional<Question> question1= Optional.of((Question)mcq);
        when(mockQuestionRepository.findById(0L)).thenReturn(question1);

        // Run the test
        final MultipleChoiceQuestion result = questionService.findMcqById(0L);

        // Verify the results
        assertThat(result).isEqualTo(mcq);
    }

    @Test
    void test_FindMcqById_McqRepositoryReturnsAbsent() {
        // Setup
        when(mockMcqRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> questionService.findMcqById(0L)).isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_FindMcqById_QuestionRepositoryReturnsAbsent() {
        // Setup
        // Configure McqRepository.findById(...).
        final Optional<MultipleChoiceQuestion> multipleChoiceQuestion = Optional.of(mcq);
        when(mockMcqRepository.findById(0L)).thenReturn(multipleChoiceQuestion);

        when(mockQuestionRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> questionService.findMcqById(0L)).isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_FindCreatorByIdOfAQuestion() {
        // Setup
        when(mockQuestionRepository.findCreatorIdOfQuestion(0L)).thenReturn(Optional.of(0L));

        // Run the test
        final Long result = questionService.findCreatorByIdOfAQuestion(0L);

        // Verify the results
        assertThat(result).isEqualTo(0L);
    }

    @Test
    void test_FindCreatorByIdOfAQuestion_QuestionRepositoryReturnsAbsent() {
        // Setup
        when(mockQuestionRepository.findCreatorIdOfQuestion(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> questionService.findCreatorByIdOfAQuestion(0L))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void test_IsMultipleChoiceQuestion() {
        // Setup
        // Configure McqRepository.findById(...).
        final Optional<MultipleChoiceQuestion> multipleChoiceQuestion = Optional.of(mcq);
        when(mockMcqRepository.findById(0L)).thenReturn(multipleChoiceQuestion);

        // Run the test
        final Boolean result = questionService.isMultipleChoiceQuestion(0L);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void test_MultipleChoiceQuestion_McqRepositoryReturnsNull() {
        // Setup
        when(mockMcqRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Boolean result = questionService.isMultipleChoiceQuestion(0L);

        // Verify the results
        assertThat(result).isFalse();
    }

}
