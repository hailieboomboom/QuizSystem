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
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;

@RestController
@RequestMapping("/api/questions") // http://localhost:8088/QuestionSystem/questions
public class QuestionController {

	public static final String CREATED_QUESTION_SUCCESS = "Question has been created";
	public static final String DELETED_QUESTION_SUCCESS = "Question has been deleted";
	public static final String UPDATED_QUESTION_SUCCESS = "Question has been updated";
	public final String COURSETAG = "course";
	public final String INTERVIEWTAG = "interview";

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

	@PostMapping("/mcqs/{active_user_id}")
	@ApiOperation(value = "create a multiple choice question",
			notes = "return success or failure message")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 201, message = CREATED_QUESTION_SUCCESS),
			@io.swagger.annotations.ApiResponse(code = 404, message = "User not found"),
			@io.swagger.annotations.ApiResponse(code = 400, message = "1. provide more/less than one correct option 2. provide less than one tag/ doesn't contain at least one interview or course tag")
	}
		)
	public ResponseEntity<ApiResponse> createMcq(@PathVariable Long active_user_id,@RequestBody AddMcqDto addMcqDto) {
		User user = userService.getUserById(active_user_id);
		mcoService.validateOptions(addMcqDto.getOptions());
		tagService.validateTagsFromDto(addMcqDto.getTags());
		questionService.accessControlCreateMCQ(addMcqDto.getTags(), user.getRole());
		questionService.createMCQ(addMcqDto,user);
		return  new ResponseEntity<>(new ApiResponse(true, CREATED_QUESTION_SUCCESS),HttpStatus.CREATED);
	}

	@GetMapping("/tags")
	public ResponseEntity<List<String>> getAllTags(){
		List<String> tags = tagService.findAll();
		
		return new ResponseEntity<>(tags,HttpStatus.OK);
	}
	
	@GetMapping("/tags/{tag_id}")
	public ResponseEntity<String> getTagById(@PathVariable long tag_id){
		String tagName = tagService.getTagById(tag_id);
		
		return new ResponseEntity<>(tagName,HttpStatus.OK);
	}
	
	
	@GetMapping("/mcqs/{mcqId}")
	@ApiOperation(value = "get a multiple choice question via the question id",
			notes = "return question along with tags and options")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "return question along with tags and options",response = ReturnMcqDto.class),
			@io.swagger.annotations.ApiResponse(code = 404, message = "Question Not Found")}
	)
	public ResponseEntity<AddMcqDto> getOneMcq(@PathVariable Long mcqId) {
		return  new ResponseEntity<>(questionService.getMcqDto(mcqId),HttpStatus.OK);
	}


	@GetMapping("/mcqs/{mcqId}/correct_option")
	@ApiOperation(value = "get the correction option of a multiple choice question via a question id",
			notes = "return the correct option")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "return question along with tags and options",response = CorrectOptionDto.class),
			@io.swagger.annotations.ApiResponse(code = 404, message = "Question Not Found")}
	)
	public ResponseEntity<CorrectOptionDto> getCorrectOption(@PathVariable Long mcqId) {

		return  new ResponseEntity<>(mcoService.getRightOption(mcqId),HttpStatus.OK);
	}


	@GetMapping("/{userId}/mcqs")
	@ApiOperation(value = "get all multiple choice question created by a user based on userId",
			notes = "return success or failure message")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "return a list of multiple choice questions created by an user",response = ReturnMcqDto.class, responseContainer = "List"),
			@io.swagger.annotations.ApiResponse(code = 404, message = "User Not Found")}
	)
	public ResponseEntity<List> getAllMcqCreatedByAnUser(@PathVariable Long userId) {
		return  new ResponseEntity<>(questionService.getAllMcqCreatedByAnUser(userId),HttpStatus.OK);
	}


	@GetMapping("/mcqs")
	@ApiOperation(value = "get all multiple choice question from question bank",
			notes = "return success or failure message")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "return all questions from question bank ",response = ReturnMcqDto.class, responseContainer = "List")
	}
	)
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

	@DeleteMapping("/mcqs/{mcqId}")
	@ApiOperation(value = "delete a multiple choice question based on questionId",
			notes = "return success or failure message")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "return success message"),
			@io.swagger.annotations.ApiResponse(code = 404, message = "Question Not Found/Options Not Found for this Question")}
	)
	public ResponseEntity<ApiResponse> deleteOneMcqById(@PathVariable Long mcqId) {
		questionService.deleteOneMcq(mcqId);

		return  new ResponseEntity<>(new ApiResponse(true, DELETED_QUESTION_SUCCESS),HttpStatus.OK);
	}
	
	 
	//HAILIE NOTE: UPDATE DELETE MCQ ADD LOGGED IN USER ID INTO PATH
	@DeleteMapping("/mcqs/{mcqId}/{active_user_id}")
	public ResponseEntity<ApiResponse> deleteOneMcqByIdVer2(@PathVariable Long mcqId, @PathVariable Long active_user_id) {
		questionService.deleteOneMcqByRole(mcqId, active_user_id);

		return  new ResponseEntity<>(new ApiResponse(true, DELETED_QUESTION_SUCCESS),HttpStatus.OK);
	}


	@PutMapping("/mcqs/{mcqId}")
	@ApiOperation(value = "update a multiple choice question by questionId",
			notes = "return success or failure message")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = UPDATED_QUESTION_SUCCESS),
			@io.swagger.annotations.ApiResponse(code = 404, message = "Question not found"),
			@io.swagger.annotations.ApiResponse(code = 400, message = "1. provide more/less than one correct option 2.provide less than one tag/ doesn't contain at least one interview or course tag")

	})
	public ResponseEntity<ApiResponse> updateOneMcqById(@PathVariable Long mcqId,@RequestBody AddMcqDto addMcqDto) {
		questionService.updateMCQ(addMcqDto,mcqId);
		return  new ResponseEntity<>(new ApiResponse(true, UPDATED_QUESTION_SUCCESS),HttpStatus.OK);
	}
	
	//HAILIE NOTE: UPDATE MCQ: ADD LOGGED IN USER ID INTO PATH
	@PutMapping("/mcqs/{mcqId}/{active_user_id}")
	public ResponseEntity<ApiResponse> updateOneMcqByIdVer2(@PathVariable Long mcqId,@RequestBody AddMcqDto addMcqDto, @PathVariable Long active_user_id) {
		mcoService.validateOptions(addMcqDto.getOptions());
		tagService.validateTagsFromDto(addMcqDto.getTags());
		questionService.updateMCQByRole(addMcqDto, mcqId, active_user_id);
		return  new ResponseEntity<>(new ApiResponse(true, UPDATED_QUESTION_SUCCESS),HttpStatus.OK);
	}
	

	@GetMapping("/questionBank/{questionBankType}")
	@ApiOperation(value = "get interview/question question bank ",
			notes = "return success or failure message")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = UPDATED_QUESTION_SUCCESS,response = ReturnMcqDto.class, responseContainer = "List"),
			@io.swagger.annotations.ApiResponse(code = 404, message = "Question bank not found")

	})
	public ResponseEntity<List> getInterviewQuestionBank(@NonNull @PathVariable String questionBankType){
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
		if((saqDto.getTags().contains(COURSETAG) == false)
				&& (saqDto.getTags().contains(INTERVIEWTAG) == false)) {
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
		if((saqDto.getTags().contains(COURSETAG) == false)
				&& (saqDto.getTags().contains(INTERVIEWTAG) == false)) {
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
