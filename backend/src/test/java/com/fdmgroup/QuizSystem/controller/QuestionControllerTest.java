package com.fdmgroup.QuizSystem.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.McqDto.AddMcqDto;
import com.fdmgroup.QuizSystem.dto.McqDto.CorrectOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.ReturnMcqDto;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.MultipleChoiceOptionService;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.TagService;
import com.fdmgroup.QuizSystem.service.UserService;

/**
 * Test cases for QuestionController
 * Please note that test cases for APIs related to Short Answer Questions 
 * are not available since they are not yet implemented in the system.
 * 
 * @author Hailie Long, Chris Tang
 *
 */
@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {
	
	private QuestionController questionController;
	
	@Mock
	QuestionService mockQuestionService;
	
	@Mock
	MultipleChoiceOptionService mockMcoService;
	
	@Mock
	UserService mockUserService;
	
	@Mock
	TagService mockTagService;
	
	private User user;
	
	private AddMcqDto addMcqDto;
	
	private CorrectOptionDto correctOptionDto;
	
	private ReturnMcqDto returnMcqDto;
	
	private QuestionGradeDTO questionGradeDto;
	
	
	@BeforeEach
	void setup() {
		questionController = new QuestionController(mockQuestionService, mockMcoService, mockUserService, mockTagService);
		
		user = new User(0L, "username", "password", "email", "firstName", "lastName",
                Role.POND);
		addMcqDto = new AddMcqDto();
		correctOptionDto = new CorrectOptionDto();
		returnMcqDto = new ReturnMcqDto();
		questionGradeDto = new QuestionGradeDTO();
	}

	@Test
    void test_CreateMcq() throws Exception {

        when(mockUserService.getUserById(0L)).thenReturn(user);
        
        questionController.createMcq(0L,addMcqDto);
        
        verify(mockMcoService).validateOptions(addMcqDto.getOptions());
        verify(mockTagService).validateTagsFromDto(addMcqDto.getTags());
        verify(mockQuestionService).accessControlCreateMCQ(addMcqDto.getTags(), Role.POND);
        verify(mockQuestionService).createMCQ(any(AddMcqDto.class), any(User.class));
    }
	
	
    @Test
    void test_GetAllTags() throws Exception {
        // Setup
        when(mockTagService.findAll()).thenReturn(List.of("interview"));
        questionController.getAllTags();
        verify(mockTagService).findAll();
    }
    
    @Test
    void test_getOneMcq_call_getMcqDto_from_questionService() throws Exception{
    	long mcqId = 1;
    	when(mockQuestionService.getMcqDto(mcqId)).thenReturn(addMcqDto);
    	ResponseEntity<AddMcqDto> expectedRes = new ResponseEntity<>(addMcqDto, HttpStatus.OK);
    	
    	ResponseEntity<AddMcqDto> result = questionController.getOneMcq(mcqId);
    	
    	verify(mockQuestionService, times(1)).getMcqDto(mcqId);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_getCorrectOption_call_getRightOption_from_mcoService() throws Exception {
    	long mcqId = 1;
    	when(mockMcoService.getRightOption(mcqId)).thenReturn(correctOptionDto);
    	ResponseEntity<CorrectOptionDto> expectedRes = new ResponseEntity<>(correctOptionDto, HttpStatus.OK);
    	
    	ResponseEntity<CorrectOptionDto> result = questionController.getCorrectOption(mcqId);
    	
    	verify(mockMcoService, times(1)).getRightOption(mcqId);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_getAllMcqCreatedByAnUser_call_from_questionService() throws Exception{
    	long userId = 1;
    	when(mockQuestionService.getAllMcqCreatedByAnUser(userId)).thenReturn(List.of(returnMcqDto));
    	ResponseEntity<List> expectedRes = new ResponseEntity<>(List.of(returnMcqDto), HttpStatus.OK);
    	
    	ResponseEntity<List> result = questionController.getAllMcqCreatedByAnUser(userId);
    	
    	verify(mockQuestionService, times(1)).getAllMcqCreatedByAnUser(userId);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_getAllMcq_call_getAllMcqQuestion_from_questionService() throws Exception{
    	when(mockQuestionService.getAllMcqQuestion()).thenReturn(List.of(returnMcqDto));
    	ResponseEntity<List<ReturnMcqDto>> expectedRes = new ResponseEntity<>(List.of(returnMcqDto), HttpStatus.OK);
    	
    	ResponseEntity<List<ReturnMcqDto>> result = questionController.getAllMcq();
    	
    	verify(mockQuestionService, times(1)).getAllMcqQuestion();
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_getAllMcqForQuizQuestion_call_getAllMcqQuestionforQuizCreation_from_questionService() throws Exception{
    	when(mockQuestionService.getAllMcqQuestionforQuizCreation()).thenReturn(List.of(questionGradeDto));
    	ResponseEntity<List<QuestionGradeDTO>> expectedRes = new ResponseEntity<>(List.of(questionGradeDto), HttpStatus.OK);
    	
    	ResponseEntity<List<QuestionGradeDTO>> result = questionController.getAllMcqforQuizCreation();
    	
    	verify(mockQuestionService, times(1)).getAllMcqQuestionforQuizCreation();
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_getMcqsForQuizEdit_calls_getMcqDtosforQuizEdit_from_questionService() throws Exception{
    	long quiz_id = 1;
    	when(mockQuestionService.getMcqDtosforQuizEdit(quiz_id)).thenReturn(List.of(questionGradeDto));
    	ResponseEntity<List<QuestionGradeDTO>> expectedRes = new ResponseEntity<>(List.of(questionGradeDto), HttpStatus.OK);
    	
    	ResponseEntity<List<QuestionGradeDTO>> result = questionController.getMcqsforQuizEdit(quiz_id);
    	
    	verify(mockQuestionService, times(1)).getMcqDtosforQuizEdit(quiz_id);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_deleteOneMcqbyId_call_deleteOneMcq_from_questionService() throws Exception{
    	long mcqId = 1;

    	ResponseEntity<ApiResponse> expectedRes = new ResponseEntity<>(new ApiResponse(true, questionController.DELETED_QUESTION_SUCCESS), HttpStatus.OK);
    	
    	ResponseEntity<ApiResponse> result = questionController.deleteOneMcqById(mcqId);
    	
    	verify(mockQuestionService, times(1)).deleteOneMcq(mcqId);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_deleteOneMcqbyIdVer2_call_deleteOneMcqByRole_from_questionService() throws Exception{
    	long mcqId = 1;
    	long active_user_id = 1;
    	ResponseEntity<ApiResponse> expectedRes = new ResponseEntity<>(new ApiResponse(true, questionController.DELETED_QUESTION_SUCCESS), HttpStatus.OK);
    	
    	ResponseEntity<ApiResponse> result = questionController.deleteOneMcqByIdVer2(mcqId, active_user_id);
    	
    	verify(mockQuestionService, times(1)).deleteOneMcqByRole(mcqId, active_user_id);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_updateOneMcqById_call_updateMcq_from_questionService() throws Exception{
    	long mcqId = 1;
    	ResponseEntity<ApiResponse> expectedRes = new ResponseEntity<>(new ApiResponse(true, questionController.UPDATED_QUESTION_SUCCESS), HttpStatus.OK);
    	
    	ResponseEntity<ApiResponse> result = questionController.updateOneMcqById(mcqId, addMcqDto);
    	
    	verify(mockQuestionService, times(1)).updateMCQ(addMcqDto, mcqId);
    	assertEquals(expectedRes, result);
    }
    
    @Test
    void test_updateOneMcqByIdVer2_call_updateMcqByRole_from_questionService() throws Exception{
    	long mcqId = 1;
    	long active_user_id = 1;
    	ResponseEntity<ApiResponse> expectedRes = new ResponseEntity<>(new ApiResponse(true, questionController.UPDATED_QUESTION_SUCCESS), HttpStatus.OK);
    	
    	ResponseEntity<ApiResponse> result = questionController.updateOneMcqByIdVer2(mcqId, addMcqDto, active_user_id);
    	
    	verify(mockMcoService, times(1)).validateOptions(addMcqDto.getOptions());
    	verify(mockTagService, times(1)).validateTagsFromDto(addMcqDto.getTags());
    	verify(mockQuestionService, times(1)).updateMCQByRole(addMcqDto, mcqId, active_user_id);
    	assertEquals(expectedRes, result);
    }
    
    
    @Test
    void test_getInterviewQuestionBank_call_getMcqBank_from_questionService() throws Exception{
    	String questionBankType = "test";
    	when(mockQuestionService.getMcqBank(questionBankType)).thenReturn(List.of(returnMcqDto));
    	ResponseEntity<List> expectedRes = new ResponseEntity<>(List.of(returnMcqDto), HttpStatus.OK);
    	
    	ResponseEntity<List> result = questionController.getInterviewQuestionBank(questionBankType);
    	
    	verify(mockQuestionService, times(1)).getMcqBank(questionBankType);
    	assertEquals(expectedRes, result);
    }

}
