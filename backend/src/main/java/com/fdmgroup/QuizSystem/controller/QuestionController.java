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
 * The controller defines question related api and handles those api requests
 */
@RestController
@RequestMapping("/api/questions") // http://localhost:8088/QuestionSystem/questions
public class QuestionController {

	public static final String CREATED_QUESTION_SUCCESS = "Question has been created";
	public static final String DELETED_QUESTION_SUCCESS = "Question has been deleted";
	public static final String UPDATED_QUESTION_SUCCESS = "Question has been updated";
	public static final String PATH_MAPPING_CREATE_MCQ = "/mcqs/{active_user_id}";
	public static final String PATH_MAPPING_GET_ALL_TAGS = "/tags";
	public static final String PATH_MAPPING_GET_TAGS_BY_TAG_ID = "/tags/{tag_id}";
	public static final String PATH_MAPPING_GET_MCQ_BY_MCQ_ID = "/mcqs/{mcqId}";
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

	@GetMapping(PATH_MAPPING_GET_ALL_TAGS)
	public ResponseEntity<List<String>> getAllTags(){
		List<String> tags = tagService.findAll();
		
		return new ResponseEntity<>(tags,HttpStatus.OK);
	}
	
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

	@GetMapping("/quizCreation/mcqs")
	public ResponseEntity<List<QuestionGradeDTO>> getAllMcqforQuizCreation() {
		return  new ResponseEntity<>(questionService.getAllMcqQuestionforQuizCreation(),HttpStatus.OK);
	}
	
	@GetMapping("/quizEdit/{quiz_id}/mcqs")
	public ResponseEntity<List<QuestionGradeDTO>> getMcqsforQuizEdit(@PathVariable long quiz_id) {
		return  new ResponseEntity<>(questionService.getMcqDtosforQuizEdit(quiz_id),HttpStatus.OK);
	}


	//HAILIE NOTE: UPDATE DELETE MCQ ADD LOGGED IN USER ID INTO PATH
	@DeleteMapping("/mcqs/{mcqId}/{active_user_id}")
	public ResponseEntity<ApiResponse> deleteOneMcqByIdVer2(@PathVariable Long mcqId, @PathVariable Long active_user_id) {
		questionService.deleteOneMcqByRole(mcqId, active_user_id);

		return  new ResponseEntity<>(new ApiResponse(true, DELETED_QUESTION_SUCCESS),HttpStatus.OK);
	}

	
	//HAILIE NOTE: UPDATE MCQ: ADD LOGGED IN USER ID INTO PATH
	@PutMapping("/mcqs/{mcqId}/{active_user_id}")
	public ResponseEntity<ApiResponse> updateOneMcqByIdVer2(@PathVariable Long mcqId,@RequestBody AddMcqDto addMcqDto, @PathVariable Long active_user_id) {
		mcoService.validateOptions(addMcqDto.getOptions());
		tagService.validateTagsFromDto(addMcqDto.getTags());
		questionService.updateMCQByRole(addMcqDto, mcqId, active_user_id);
		return  new ResponseEntity<>(new ApiResponse(true, UPDATED_QUESTION_SUCCESS),HttpStatus.OK);
	}

	/**
	 * handles the request of getting different types of question
	 * @param questionBankType question bank type could be interview/course
	 * @return a list of Multiple choice dto based on questionBankType
	 */

	@GetMapping(PATH_MAPPING_GET_QUESTION_BY_TYPE)
	public ResponseEntity<List> getDifferentQuestionBank(@NonNull @PathVariable String questionBankType){
		return  new ResponseEntity<>(questionService.getMcqBank(questionBankType),HttpStatus.OK);
	}



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
	
	// get one saq  by id
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
	
	// get all saqs
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
	
	// update saq
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
	
	// delete saq
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
	
	// view all questions (just view question details without viewing its answers)
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
	
	// view all short answer questions created by a user
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

	// view questions by tag
}
