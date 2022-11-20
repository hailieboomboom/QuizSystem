package com.fdmgroup.QuizSystem.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.dto.QuestionDto;
import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.SAQDto;
import com.fdmgroup.QuizSystem.dto.McqDto.AddMcqDto;
import com.fdmgroup.QuizSystem.dto.McqDto.CorrectOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.ReturnMcqDto;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.MultipleChoiceOptionService;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.ShortAnswerQuestionService;
import com.fdmgroup.QuizSystem.service.TagService;
import com.fdmgroup.QuizSystem.service.UserService;
import lombok.NonNull;

/**
 * 
 * QuestionController is responsible for handling frontend requests regarding questions, 
 * and send corresponding information or data back as responses.
 * 
 * Please note that APIs for Short Answer Questions (SAQs) are not yet implemented.
 * 
 * @author Chris Tang, Hailie Long
 *
 */

@RestController
@RequestMapping("/api/questions") // http://localhost:8088/QuestionSystem/questions
public class QuestionController {

	public static final String PATH_DELETE_OR_UPDATE_MCQ_BY_MCQ_AND_USER_ID = "/mcqs/{mcqId}/{active_user_id}";
	public static final String PATH_DELETE_OR_UPDATE_MCQ_BY_ID = "/mcqs/{mcqId}";
	public static final String PATH_GETTING_ALL_MCQS_FOR_QUIZ_EDIT = "/quizEdit/{quiz_id}/mcqs";
	public static final String PATH_GET_ALL_MCQ_FOR_QUIZ_CREATION = "/quizCreation/mcqs";
	public static final String CREATED_QUESTION_SUCCESS = "Question has been created";
	public static final String DELETED_QUESTION_SUCCESS = "Question has been deleted";
	public static final String UPDATED_QUESTION_SUCCESS = "Question has been updated";
	public static final String PATH_MAPPING_CREATE_MCQ = "/mcqs/{active_user_id}";
	public static final String PATH_MAPPING_GET_ALL_TAGS = "/tags";
	public static final String PATH_MAPPING_GET_TAGS_BY_TAG_ID = "/tags/{tag_id}";
	public static final String PATH_MAPPING_GET_MCQ_BY_MCQ_ID = PATH_DELETE_OR_UPDATE_MCQ_BY_ID;
	public static final String PATH_MAPPING_GET_CORRECT_OPTION_OF_MCQ = "/mcqs/{mcqId}/correct_option";
	public static final String PATH_MAPPING_GET_MCQ_CREATED_BY_A_USER = "/{userId}/mcqs";
	public static final String PATH_MAPPING_GET_ALL_MCQS = "/mcqs";
	public static final String PATH_MAPPING_GET_QUESTION_BY_TYPE = "/questionBank/{questionBankType}";
	public final String COURSE_TAG = "course";
	public final String INTERVIEW_TAG = "interview";

	@Autowired
	private QuestionService questionService;

	@Autowired
	private MultipleChoiceOptionService mcoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private TagService tagService;
	
	@Autowired
	private ShortAnswerQuestionService saqService;
	
	
	/**
	 * Constructor with fields
	 * @param questionService QuestionService 
	 * @param mcoService MultipleChoiceOptionService 
	 * @param userService UserService 
	 * @param tagService TagService
	 * @param saqService ShortAnswerQuestionService
	 */
	public QuestionController(QuestionService questionService, MultipleChoiceOptionService mcoService, UserService userService, TagService tagService, ShortAnswerQuestionService saqService) {
		this.questionService = questionService;
		this.mcoService = mcoService;
		this.userService = userService;
		this.tagService = tagService;
		this.saqService = saqService;

	}

	/**
	 * handles create multiple choice question
	 * @param active_user_id the id of user who wants to create question
	 * @param addMcqDto the multiple choice question that sent from user
	 * @return an Api response that contains the result of the creation
	 */
	@PostMapping(PATH_MAPPING_CREATE_MCQ)
	public ResponseEntity<ApiResponse> createMcq(@PathVariable Long active_user_id,@RequestBody AddMcqDto addMcqDto) {
		User user = userService.getUserById(active_user_id);
		mcoService.validateOptions(addMcqDto.getOptions());
		tagService.validateTagsFromDto(addMcqDto.getTags());
		questionService.accessControlCreateMCQ(addMcqDto.getTags(), user.getRole());
		questionService.createMCQ(addMcqDto,user);
		return  new ResponseEntity<>(new ApiResponse(true, CREATED_QUESTION_SUCCESS),HttpStatus.CREATED);
	}


	/**
	 * get all available tags in database
	 * @return List of tag names stored in response entity
	 */
	@GetMapping(PATH_MAPPING_GET_ALL_TAGS)
	public ResponseEntity<List<String>> getAllTags(){
		List<String> tags = tagService.findAll();
		
		return new ResponseEntity<>(tags,HttpStatus.OK);
	}
	

	/**
	 * get a tag by tag id stored in database
	 * @param tag_id: tag id
	 * @return tag name stored in response entity
	 */
	@GetMapping(PATH_MAPPING_GET_TAGS_BY_TAG_ID)
	public ResponseEntity<String> getTagById(@PathVariable long tag_id){
		String tagName = tagService.getTagById(tag_id);
		
		return new ResponseEntity<>(tagName,HttpStatus.OK);
	}



	/**
	 * handles request of getting a multiple choice question
	 * @param mcqId the id of a multiple choice question
	 * @return a list of Multiple choice dto that contains tags/multiple choice question and options
	 */
	@GetMapping(PATH_MAPPING_GET_MCQ_BY_MCQ_ID)
	public ResponseEntity<AddMcqDto> getOneMcq(@PathVariable Long mcqId) {
		return  new ResponseEntity<>(questionService.getMcqDto(mcqId),HttpStatus.OK);
	}


	/**
	 * handles request of getting the correct answer of a multiple choice question
	 * @param mcqId the id of a multiple choice question
	 * @return correct option dto
	 */
	@GetMapping(PATH_MAPPING_GET_CORRECT_OPTION_OF_MCQ)
	public ResponseEntity<CorrectOptionDto> getCorrectOption(@PathVariable Long mcqId) {

		return  new ResponseEntity<>(mcoService.getRightOption(mcqId),HttpStatus.OK);
	}


	/**
	 * handles request of getting all multiple choice questions created by a user
	 * @param userId the id of the user
	 * @return a list of Multiple choice dto that contains tags/multiple choice question and options
	 */
	@GetMapping(PATH_MAPPING_GET_MCQ_CREATED_BY_A_USER)
	public ResponseEntity<List> getAllMcqCreatedByAnUser(@PathVariable Long userId) {
		return  new ResponseEntity<>(questionService.getAllMcqCreatedByAnUser(userId),HttpStatus.OK);
	}



	/**
	 * handles request of getting all multiple choice questions in database
	 * @return a list of Multiple choice dto that contains tags/multiple choice question and options
	 */
	@GetMapping(PATH_MAPPING_GET_ALL_MCQS)
	public ResponseEntity<List<ReturnMcqDto>> getAllMcq() {
		return  new ResponseEntity<>(questionService.getAllMcqQuestion(),HttpStatus.OK);
	}

	/**
	 * get all mcqs in another format for quiz creation
	 * @return all mcqs in another format (list of QuestionGradeDto), included in response entity
	 */
	@GetMapping(PATH_GET_ALL_MCQ_FOR_QUIZ_CREATION)
	public ResponseEntity<List<QuestionGradeDTO>> getAllMcqforQuizCreation() {
		return  new ResponseEntity<>(questionService.getAllMcqQuestionforQuizCreation(),HttpStatus.OK);
	}
	
	/**
	 * get all mcqs that are not involved in current quiz yet when editing the quiz
	 * @param quiz_id id of the quiz to be edited
	 * @return all mcqs that are not involved in current quiz yet, in the format of QuestionGradeDTO list
	 */
	@GetMapping(PATH_GETTING_ALL_MCQS_FOR_QUIZ_EDIT)
	public ResponseEntity<List<QuestionGradeDTO>> getMcqsforQuizEdit(@PathVariable long quiz_id) {
		return  new ResponseEntity<>(questionService.getMcqDtosforQuizEdit(quiz_id),HttpStatus.OK);
	}


	/**
	 * delete one mcq by its id
	 * @param mcqId: id of the mcq to be deleted
	 * @return api response involved in response entity indicating successful deletion
	 */
	@DeleteMapping(PATH_DELETE_OR_UPDATE_MCQ_BY_ID)
	public ResponseEntity<ApiResponse> deleteOneMcqById(@PathVariable Long mcqId) {
		questionService.deleteOneMcq(mcqId);

		return  new ResponseEntity<>(new ApiResponse(true, DELETED_QUESTION_SUCCESS),HttpStatus.OK);
	}
	
	 
	/**
	 * delete one mcq by its id and user id. for instance, a student cannot delete any questions that are not created by himself
	 * @param mcqId : id of mcq to be deleted
	 * @param active_user_id: current logged in user id
	 * @return api response involved in response entity indicating successful deletion
	 */
	@DeleteMapping(PATH_DELETE_OR_UPDATE_MCQ_BY_MCQ_AND_USER_ID)
	public ResponseEntity<ApiResponse> deleteOneMcqByIdVer2(@PathVariable Long mcqId, @PathVariable Long active_user_id) {
		questionService.deleteOneMcqByRole(mcqId, active_user_id);

		return  new ResponseEntity<>(new ApiResponse(true, DELETED_QUESTION_SUCCESS),HttpStatus.OK);
	}



	/**
	 * update an mcq according to user update input
	 * @param mcqId: id of mcq to be updated
	 * @param addMcqDto: question details to be updated
	 * @return api response involved in response entity indicating successful update
	 */
	@PutMapping(PATH_DELETE_OR_UPDATE_MCQ_BY_ID)
	public ResponseEntity<ApiResponse> updateOneMcqById(@PathVariable Long mcqId,@RequestBody AddMcqDto addMcqDto) {
		questionService.updateMCQ(addMcqDto,mcqId);
		return  new ResponseEntity<>(new ApiResponse(true, UPDATED_QUESTION_SUCCESS),HttpStatus.OK);
	}

	
	/**
	 * update an mcq according to user update input and user's role. For instance, a student cannot update questions not created by himself
	 * @param mcqId: id of mcq to be updated
	 * @param addMcqDto: question details to be updated
	 * @param active_user_id: id of logged in user
	 * @return api response involved in response entity indicating successful update if both user's role and input are valid
	 */
	@PutMapping(PATH_DELETE_OR_UPDATE_MCQ_BY_MCQ_AND_USER_ID)
	public ResponseEntity<ApiResponse> updateOneMcqByIdVer2(@PathVariable Long mcqId,@RequestBody AddMcqDto addMcqDto, @PathVariable Long active_user_id) {
		mcoService.validateOptions(addMcqDto.getOptions());
		tagService.validateTagsFromDto(addMcqDto.getTags());
		questionService.updateMCQByRole(addMcqDto, mcqId, active_user_id);
		return  new ResponseEntity<>(new ApiResponse(true, UPDATED_QUESTION_SUCCESS),HttpStatus.OK);
	}


	/**
	 * get all questions of particular type (course/interview)
	 * 
	 * @param questionBankType type of questions to be returned
	 * @return list of qualified questions returned in form of list of ReturnMcqDto,
	 *         involved in response entity
	 */
	@GetMapping(PATH_MAPPING_GET_QUESTION_BY_TYPE)
	public ResponseEntity<List> getDifferentQuestionBank(@NonNull @PathVariable String questionBankType){
		return  new ResponseEntity<>(questionService.getMcqBank(questionBankType),HttpStatus.OK);
	}


	
	/////// BELOW ARE APIs RELATED TO SHORT ANSWER QUESTIONS, WHICH ARE NOT YET IMPLEMENTED ////////
	

	
	/**
	 * create a short answer question (saq)
	 * @param saqDto saq details submitted by user
	 * @return api response indicating successful creation, sent in response entity
	 */
	@PostMapping("/saqs")
	public ResponseEntity<ApiResponse> postShortAnswerQuestion(@RequestBody SAQDto saqDto) {
		
		//check if question with same questionDetails exists
		boolean ifExists = saqService.ifShortAnswerQuestionExists(saqDto.getQuestionDetails());
		if(ifExists) {
			return new ResponseEntity<>(new ApiResponse(false, "question already exists"), HttpStatus.BAD_REQUEST);
		}
		
		// check if tag contains interview/course
		if((saqDto.getTags().contains(COURSE_TAG) == false)
				&& (saqDto.getTags().contains(INTERVIEW_TAG) == false)) {
			return new ResponseEntity<>(new ApiResponse(false, "please tag question with course or interview"), HttpStatus.BAD_REQUEST);
		}
		
		ShortAnswerQuestion saq = new ShortAnswerQuestion();
		User creator = userService.getUserById(saqDto.getUserId());
		saq.setCreator(creator);
		saq.setCorrectAnswer(saqDto.getCorrectAnswer());
		saq.setQuestionDetails(saqDto.getQuestionDetails());
		
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			saq.addOneTag(t);
		}
		
		questionService.save(saq);
		
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			t.addOneQuestion(saq);
			tagService.save(t);
		}
		
		
		return new ResponseEntity<>(new ApiResponse(true, "create short answer question success"), HttpStatus.CREATED);
	}
	
	/**
	 * get one saq by id
	 * @param id: id of the saq to be fetched
	 * @return saq details sent in the form of saqDto
	 */
	@GetMapping("/saqs/{id}")
	public SAQDto getOneShortAnswerQuestion(@PathVariable Long id) {
		
		ShortAnswerQuestion saq = saqService.findById(id);
		SAQDto saqDto = new SAQDto();
		saqDto.setUserId(saq.getCreator().getId());
		saqDto.setCorrectAnswer(saq.getCorrectAnswer());
		saqDto.setQuestionDetails(saq.getQuestionDetails());
		saqDto.setSaqId(id);
		List<String> tagnames = new ArrayList<String>();
		for(Tag t: saq.getTags()) {
			tagnames.add(t.getTagName());
		}
		saqDto.setTags(tagnames);
		return saqDto;
	}
	
	
	/**
	 * get all saqs available in database
	 * @return list of SAQDto in including all saqs
	 */
	@GetMapping("/saqs")
	public List<SAQDto> getAllShortAnswerQuestion(){
		List<ShortAnswerQuestion> saqs = saqService.findAll();
		List<SAQDto> saqDtos = new ArrayList<SAQDto>();
		
		for(ShortAnswerQuestion saq: saqs) {
			SAQDto saqDto = new SAQDto();
			saqDto.setUserId(saq.getCreator().getId());
			saqDto.setCorrectAnswer(saq.getCorrectAnswer());
			saqDto.setQuestionDetails(saq.getQuestionDetails());
			saqDto.setSaqId(saq.getId());
			List<String> tagnames = new ArrayList<String>();
			for(Tag t: saq.getTags()) {
				tagnames.add(t.getTagName());
			}
			saqDto.setTags(tagnames);
			saqDtos.add(saqDto);
		}
		return saqDtos;
	}
	
	
	/**
	 * update one saq by id
	 * @param saqDto: updated question details submitted from user
	 * @param id: id of the question to be updated
	 * @return api response indicating successful update, sent in response entity
	 */
	@PutMapping("/saqs/{id}")
	public ResponseEntity<ApiResponse> updateShortAnswerQuestion(@RequestBody SAQDto saqDto, @PathVariable Long id){
		
		ShortAnswerQuestion saq = saqService.findById(id);
		
		//check if question with same questionDetails exists
		boolean ifExists = saqService.ifShortAnswerQuestionExists(saqDto.getQuestionDetails());
		if(ifExists) {
			Question foundQuestion = saqService.getByQuestionDetails(saqDto.getQuestionDetails());
			
			// if different question has same details with updated one, terminate the update
			if(foundQuestion.getId() != id) {
				return new ResponseEntity<>(new ApiResponse(false, "question already exists"), HttpStatus.BAD_REQUEST);
			}
		}
		
		// check if tag contains interview/course
		if((saqDto.getTags().contains(COURSE_TAG) == false)
				&& (saqDto.getTags().contains(INTERVIEW_TAG) == false)) {
			return new ResponseEntity<>(new ApiResponse(false, "please tag question with course or interview"), HttpStatus.BAD_REQUEST);
		}
		
		saq.setCorrectAnswer(saqDto.getCorrectAnswer());
		saq.setQuestionDetails(saqDto.getQuestionDetails());
		
		// remove question from tags
		Set<Tag> oldtags = saq.getTags();
		for(Tag t: oldtags) {
			t.removeOneQuestion(saq);
			tagService.save(t);
		}
		// update tags by adding new tags
		saq.setTags(new HashSet<Tag>());
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			saq.addOneTag(t);
		}
		
        questionService.save(saq);
		
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			t.addOneQuestion(saq);
			tagService.save(t);
		}
		
		return new ResponseEntity<>(new ApiResponse(true, "update short answer question success"), HttpStatus.OK);
		
	}
	
	/**
	 * delete one saq by its id
	 * @param id: id of the saq to be deleted
	 * @return: api response indicating successful deletion, sent in response entity
	 */
	@DeleteMapping("/saqs/{id}")
	public ResponseEntity<ApiResponse> deleteOneShortAnswerQuestion(@PathVariable Long id){
		
		ShortAnswerQuestion saq = saqService.findById(id);
		
		// remove question from tags
		Set<Tag> oldtags = saq.getTags();
		for(Tag t: oldtags) {
			t.removeOneQuestion(saq);
			tagService.save(t);
		}
		
		// delete saq
		questionService.remove(saq);
		
		return new ResponseEntity<>(new ApiResponse(true, "delete short answer question success"), HttpStatus.OK);
		
	}
	
	/**
	 * view all questions regardless of their format
	 * @return a list of questionDto involving all question details
	 */
	@GetMapping
	public List<QuestionDto> getAllQuestions(){
		List<Question> questions = questionService.findAllQuestions();
		List<QuestionDto> questionDTOs = new ArrayList<QuestionDto>();
		
		for(Question q: questions) {
			QuestionDto qDto = new QuestionDto();
			qDto.setQuestionId(q.getId());
			qDto.setUserId(q.getCreator().getId());
			qDto.setQuestionDetails(q.getQuestionDetails());
			List<String> tagnames = new ArrayList<String>();
			for(Tag t: q.getTags()) {
				tagnames.add(t.getTagName());
			}
			qDto.setTags(tagnames);
			questionDTOs.add(qDto);
		}
		
		return questionDTOs;
	}
	
	
	/**
	 * view all saqs created by one user
	 * @param userId: id of the creator to the saqs
	 * @return list of saqDto involving all created question details
	 */
	@GetMapping("/{userId}/saqs")
	public List<SAQDto> getAllShortAnswerQuestionsByCreator(@PathVariable Long userId){
		
		List<SAQDto> saqDtos = new ArrayList<SAQDto>();
		
		User creator = userService.getUserById(userId);
		// get all questions created by users
		// go through the questions and check if question is an saq
		// add all saqs to the returned saqDto list
		List<Question> questionsByUser = questionService.findQuestionsByCreator(creator);
		List<Long> saqIds = saqService.getAllSaqIds();
		
		for(Question q: questionsByUser) {
			if(saqIds.contains(q.getId())) {
				ShortAnswerQuestion saq = saqService.findById(q.getId());
				SAQDto saqDto = new SAQDto();
				saqDto.setUserId(q.getCreator().getId());
				saqDto.setCorrectAnswer(saq.getCorrectAnswer());
				saqDto.setQuestionDetails(saq.getQuestionDetails());
				saqDto.setSaqId(saq.getId());
				List<String> tagnames = new ArrayList<String>();
				for(Tag t: saq.getTags()) {
					tagnames.add(t.getTagName());
				}
				saqDto.setTags(tagnames);
				saqDtos.add(saqDto);
			}
		}
		
		return saqDtos;
	}

}
