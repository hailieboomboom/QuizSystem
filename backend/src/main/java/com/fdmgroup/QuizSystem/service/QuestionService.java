package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fdmgroup.QuizSystem.exception.McqException.NotEnoughAccessException;
import com.fdmgroup.QuizSystem.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.McqDto.AddMcqDto;
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.ReturnMcqDto;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.TagNotValidException;

import com.fdmgroup.QuizSystem.repository.McqRepository;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;



/**
 * Question service handles CRUD and access control logic for questions
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class QuestionService {

	public static final String MCQ_TAG_NAME = "mcq";
	public static final String QUESTION_NOT_FOUND_ERROR = "Question Not Found";
	public static final String CREATOR_NOT_FOUND_ERROR = "You can't modify this question, because we couldn't find the creator of this question";
	public static final String NOT_ENOUGH_TAG_ERROR = "Please provide at least one tag";
	public static final String NOT_CREATOR_ERROR = "You can't modify this question, because you are not the creator";
	public static final String INTERVIEW_TAG = "interview";
	public static final String COURSE_TAG = "course";
	@Autowired
	QuizAttemptService quizAttemptService;

	@Autowired
	QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;
	@Autowired
    private QuestionRepository questionRepository;
	@Autowired
	private McqRepository mcqRepository;
	@Autowired
	private TagService tagService;
	@Autowired
	private MultipleChoiceOptionService multipleChoiceOptionService;
	@Autowired
	private UserService userService;

	@Autowired
	private QuizQuestionGradeRepository qqgRepository;


	/**
	 * save a question
	 * @param question question object
	 * @return managed question
	 */
	public Question save(Question question) {
	        return questionRepository.save(question);
	}

	/**
	 * fina all question in database
	 * @return a list of questionObject
	 */

	public List<Question> findAllQuestions(){
		return questionRepository.findAll();
	}

	/**
	 * find Question based on id
	 * @param id question id
	 * @return a question found in database
	 */
	public Question findById(Long id) {
		Optional<Question> opQuestion = questionRepository.findById(id);
		if(opQuestion.isEmpty()) {
			return null;
		}
		return opQuestion.get();
	}

	/**
	 * remove a question
	 * @param question the question object will be removed
	 */
	public void remove(Question question) {
		questionRepository.delete(question);
	}

	/**
	 * find all questions created by a user.
	 * @param creator the creator
	 * @return a list of questions created by this user.
	 */
	public List<Question> findQuestionsByCreator(User creator){
		return questionRepository.findByCreator(creator);
	}


	/**
	 * This function handles the logic of creating multiple choice question.
	 * @param addMcqDto the dto contains mcq information that was sent from front end
	 * @param user the user who wants to create
	 */
	@Transactional
	public void createMCQ(AddMcqDto addMcqDto, User user) {
		MultipleChoiceQuestion newQuestion = new MultipleChoiceQuestion();
		newQuestion.setCreator(user);
		newQuestion.setQuestionDetails(addMcqDto.getQuestionDetails());

		newQuestion = (MultipleChoiceQuestion) save(newQuestion);

		List<String> mcqTag = addMcqDto.getTags();
		mcqTag.add(MCQ_TAG_NAME);
		newQuestion.setTags(tagService.getTagsFromDto(mcqTag));
		newQuestion.setMcoptions(multipleChoiceOptionService.createListOfOption(addMcqDto.getOptions(), newQuestion));
		save(newQuestion);
	}

	/**
	 * This function check whether the user has the right to create a certain type of mcq.
	 * @param tags tags are from front end
	 * @param role the role of a user(in_training/beached/pond student or sales/ trainer)
	 */
	public void accessControlCreateMCQ(List<String> tags, Role role) {
		// check tag list is empty or not
		if(tags==null || tags.isEmpty())
			throw new TagNotValidException(NOT_ENOUGH_TAG_ERROR);
		//on training student can't create interview question
		if(role.equals(Role.TRAINING)&& tags.contains(INTERVIEW_TAG)){
			throw new NotEnoughAccessException("Student cannot create interview Questions");
		} else if( role.equals(Role.AUTHORISED_SALES )&& tags.contains(COURSE_TAG)){
			 throw new NotEnoughAccessException("Sales cannot create course-related Questions");
		}
	}

	/**
	 * The function handles the logic of update the multiple choice question
	 * @param addMcqDto the dto contains mcq information that was sent from front end
	 * @param mcqId the id of multiple choice question
	 */
 @Transactional
	public void updateMCQ(AddMcqDto addMcqDto, long mcqId) {
		MultipleChoiceQuestion originalMcq = findMcqById(mcqId);
		deleteAttempt(mcqId);
		List<MultipleChoiceOption> updatedOptions = multipleChoiceOptionService.updateMcqOption(addMcqDto.getOptions(), originalMcq);

		originalMcq.setQuestionDetails(addMcqDto.getQuestionDetails());
		originalMcq = (MultipleChoiceQuestion) save(originalMcq);

		Long userId = findCreatorByIdOfAQuestion(originalMcq.getId());
	    User user = userService.getUserById(userId);
		originalMcq.setCreator(user);
		originalMcq.setMcoptions(updatedOptions);
		originalMcq.setTags(tagService.getTagsFromDto(addMcqDto.getTags()));
		this.save(originalMcq);

	}

	/**
	 * Access control for update MCQ
	 * sales cannot update course question
	 * @param addMcqDto dto are sent from frontend, it contains updated information
	 * @param mcqId id of the question will be updated
	 * @param activeUserId the user who wants to update the mcq
	 */
	public void updateMCQByRole(AddMcqDto addMcqDto, long mcqId, long activeUserId) {
		MultipleChoiceQuestion originalMcq = findMcqById(mcqId);
		Set<Tag> originalMCQTags = originalMcq.getTags();
		boolean  isUserValid = false;
		// get user role
		User currentUser = userService.getUserById(activeUserId);
		// if current user is a student, he can only update his created question
		if(currentUser instanceof Student) {
			Long creatorId = findCreatorByIdOfAQuestion(originalMcq.getId());
			if(!creatorId.equals(activeUserId)) {
				throw new NoDataFoundException(NOT_CREATOR_ERROR);
			}else {
				updateMCQ(addMcqDto, mcqId);
			}
		}
		// if current user is a trainer, he can only update questions of course content type
		else if(currentUser.getRole().equals(Role.AUTHORISED_TRAINER)) {
			// check question contains interview tag
			for(Tag t:originalMCQTags) {
				if(t.getTagName().equals(COURSE_TAG)) {
					break;
				}
			}
			updateMCQ(addMcqDto, mcqId);
		}
		// if current user is a sales, he can only update questions of interview content type
		else if(currentUser.getRole().equals(Role.AUTHORISED_SALES)) {
			// check question contains interview tag
			for(Tag t:originalMCQTags) {
				if(t.getTagName().equals(INTERVIEW_TAG)) {
					isUserValid = true;
					break;
				}
			}
			if(isUserValid) {
				updateMCQ(addMcqDto, mcqId);
			}else {
				throw new NoDataFoundException("You can't modify this question, because sales can only modify interview questions");
			}
		}
		
	}

	/**
	 * get all question for creating quiz
	 * @return a list of QuestionGradeDto which contains question and grades of each question
	 */
	public List<QuestionGradeDTO> getAllMcqQuestionforQuizCreation() {
		var mcqQuestions = mcqRepository.findAll();

		List<QuestionGradeDTO> mcqDtoList = new ArrayList<>();

		for (MultipleChoiceQuestion question : mcqQuestions) {
			QuestionGradeDTO mcqDto = new QuestionGradeDTO();
			mcqDto.setGrade(0);
			mcqDto.setQuestionId(question.getId());
			mcqDto.setQuestionDetails(question.getQuestionDetails());
			mcqDto.setTags(question.getTags());
			mcqDtoList.add(mcqDto);		
		}

		return mcqDtoList;
	}

	/**
	 * get all question inside a quiz
	 * @param quiz_id the id of a quiz
	 * @return a list of QuestionGradeDto which contains question and grades of each question
	 */
	public List<QuestionGradeDTO> getMcqDtosforQuizEdit(long quiz_id){
		List<QuestionGradeDTO> dtos = new ArrayList<>();
		List<MultipleChoiceQuestion> involvedQuestions = new ArrayList<>();
		List<MultipleChoiceQuestion> allmcqs = mcqRepository.findAll();
		List<QuizQuestionGrade> qqgs =  qqgRepository.findAllByQuizId(quiz_id);
		for(QuizQuestionGrade qqg: qqgs) {
			MultipleChoiceQuestion q = (MultipleChoiceQuestion) findById(qqg.getKey().getQuestionId());
			involvedQuestions.add(q);
		}
		for(MultipleChoiceQuestion mcq: allmcqs) {
			if(!involvedQuestions.contains(mcq)) {
				QuestionGradeDTO mcqDto = new QuestionGradeDTO();
				mcqDto.setGrade(0);
				mcqDto.setQuestionId(mcq.getId());
				mcqDto.setQuestionDetails(mcq.getQuestionDetails());
				mcqDto.setTags(mcq.getTags());
				dtos.add(mcqDto);	
			}
		}
		
		return dtos;
	}

	/**
	 * This function returns all multiple choice questions in the database
	 * @return a list of McqDto which will be sent to frontend
	 */
	public List<ReturnMcqDto> getAllMcqQuestion() {

		List<ReturnMcqDto> mcqDtoList = new ArrayList<>();

		for (MultipleChoiceQuestion question : mcqRepository.findAll())
			mcqDtoList.add(getReturnMcqDto(question));

		return mcqDtoList;
	}

	/**
	 * This function returns all multiple choice question created by a user
	 * @param userId user Id
	 * @return a list of McqDto which will be sent to frontend
	 */
	public List<ReturnMcqDto> getAllMcqCreatedByAnUser(long userId) {

		User user = userService.getUserById(userId);
		List<Question> mcqQuestions = questionRepository.
				findAllByCreatorId(user.getId());
		List<ReturnMcqDto> mcqDtoList = new ArrayList<>();
		for (Question question : mcqQuestions) {
			if (!isMultipleChoiceQuestion(question.getId()))
				continue;
			mcqDtoList.add(getReturnMcqDto(question));
		}
		return mcqDtoList;
	}

	/**
	 * This function transfer multiple choice question to a mcq dto.
	 * @param question question object fetched from database
	 * @return multiple choice question dto
	 */
	ReturnMcqDto getReturnMcqDto(Question question) {
		ReturnMcqDto returnMcqDto = new ReturnMcqDto();
		returnMcqDto.setQuestionId(question.getId());
		returnMcqDto.setQuestionDetail(question.getQuestionDetails());
		returnMcqDto.setTags(question.getTags().stream().map(tag -> tag.getTagName()).toList());
		returnMcqDto.setMcqOptionDtoList(multipleChoiceOptionService.listOptionsForMcq(question.getId()));
		return returnMcqDto;
	}


	/**
	 * This function
	 * @param questionId THE ID OF A QUESTION
	 * @return AddMcqDto
	 */
	public AddMcqDto getMcqDto(Long questionId) {
		Question mcq = findMcqById(questionId);
		Long userId = findCreatorByIdOfAQuestion(questionId);

		List<String> tagList = mcq.getTags().stream().map(tag -> tag.getTagName()).collect(Collectors.toList());
		List<McqOptionDto> optionList = multipleChoiceOptionService.listOptionsForMcq(questionId);
		AddMcqDto addMcqDto = new AddMcqDto();
		addMcqDto.setQuestionDetails(mcq.getQuestionDetails());
		addMcqDto.setUserId(userId);
		addMcqDto.setTags(tagList);
		addMcqDto.setOptions(optionList);

		return addMcqDto;
	}

	/**
	 * this function will fetch all questions by type
	 * @param type type could be interview or question
	 * @return a list of multiple choice dto that will be sent to frontend
	 */
	public List<ReturnMcqDto> getMcqBank(String type) {
		if(!type.equals(COURSE_TAG)&&!type.equals(INTERVIEW_TAG))
			throw new TagNotValidException("The question bank " + type + " doesn't exist.");
		List<MultipleChoiceQuestion> mcqQuestions = mcqRepository.findAll();
		List<ReturnMcqDto> mcqDtoList = new ArrayList<>();
		ReturnMcqDto returnMcqDto;
		for (MultipleChoiceQuestion question : mcqQuestions){
			returnMcqDto = getReturnMcqDto(question);
			if( returnMcqDto.getTags().stream().anyMatch(tag-> tag.equals(type)))
				mcqDtoList.add(returnMcqDto) ;
		}
		return mcqDtoList;
	}


	/**
	 * This function delete single multiple choice question, related options
	 * @param questionId The QuestionId of the question that should be deleted
	 */
	public void deleteOneMcq(Long questionId) {
        deleteAttempt(questionId);
		MultipleChoiceQuestion mcq = findMcqById(questionId);
		mcq.setTags(null);
		MultipleChoiceQuestion returned = questionRepository.save(mcq);
		multipleChoiceOptionService.deleteQuestionOptions(questionId);
		questionRepository.delete(returned);

	}
	
	public void deleteOneMcqByRole(Long questionId, Long activeUserId) {
		MultipleChoiceQuestion originalMcq = findMcqById(questionId);
		Set<Tag> originalMCQTags = originalMcq.getTags();
		boolean  isUserValid = false;
		// get user role
		User currentUser = userService.getUserById(activeUserId);
		// if current user is a student, he can only delete his created question
		if(currentUser instanceof Student) {
			Long creatorId = findCreatorByIdOfAQuestion(originalMcq.getId());
			if(!creatorId.equals(activeUserId)) {
				throw new NoDataFoundException("You can't delete this question, because you are not the creator");
			}else {
				deleteOneMcq(questionId);
			}
		}
		// if current user is a trainer, he can only delete questions of course content type
		else if(currentUser.getRole().equals(Role.AUTHORISED_TRAINER)) {
			// check question contains interview tag
			for(Tag t:originalMCQTags) {
				if(t.getTagName().equals(COURSE_TAG)) {
					isUserValid = true;
					break;
				}
			}
			if(isUserValid) {
				deleteOneMcq(questionId);
			}else {
				throw new NoDataFoundException("You can't delete this question, because trainer can only delete course content questions");
			}
		}
		// if current user is a sales, he can only delete questions of interview content type
		else if(currentUser.getRole().equals(Role.AUTHORISED_SALES)) {
			// check question contains interview tag
			for(Tag t:originalMCQTags) {
				if(t.getTagName().equals(INTERVIEW_TAG)) {
					isUserValid = true;
					break;
				}
			}
			if(isUserValid) {
				deleteOneMcq(questionId);
			}else {
				throw new NoDataFoundException("You can't delete this question, because sales can only delete interview questions");
			}
		}
	}

	/**
	 * this function will find question by question
	 * @param questionId the id of a question
	 * @return A Multiple choice question
	 */
	
	public MultipleChoiceQuestion findMcqById(Long questionId) {
		if(!isMultipleChoiceQuestion(questionId)){
			throw new NoDataFoundException(QUESTION_NOT_FOUND_ERROR);
		}

		Optional<Question> optionalMCQ = questionRepository.findById(questionId);
		if(optionalMCQ.isEmpty()){
			throw new NoDataFoundException(QUESTION_NOT_FOUND_ERROR);
		}

		return (MultipleChoiceQuestion) optionalMCQ.get();
	}

	/**
	 * this function will find the id of the creator of a question
	 * @param questionId the question id
	 * @return THE ID OF A CREATOR
	 */
	public Long findCreatorByIdOfAQuestion(long questionId) {
		Optional<Long> optionalId = questionRepository.findCreatorIdOfQuestion(questionId);
		if (optionalId.isEmpty())
			throw new NoDataFoundException(CREATOR_NOT_FOUND_ERROR);
		return optionalId.get();
	}


	/**
	 * this function checks the whether the question is a multiple choice question
	 * @param questionId the id of a question
	 * @return true or false
	 */
	public Boolean isMultipleChoiceQuestion(long questionId) {
		Optional<MultipleChoiceQuestion> mcqOptional = mcqRepository.findById(questionId);
		return mcqOptional.isPresent();
	}

	/**
	 * this function will delete attempts that contains an updated question
	 * @param questionId the id of a question
	 */
	void deleteAttempt(Long questionId) {
		List<Long> mcqAttemptList = quizQuestionMCQAttemptRepository.findByMcqId(questionId);
		for (Long attempt : mcqAttemptList) {
			var result = quizAttemptService.findQuizAttemptByQuizId(attempt);
			result.forEach(quizAttempt -> quizAttemptService.deleteAttempt(quizAttempt));
		}
	}

   
}
