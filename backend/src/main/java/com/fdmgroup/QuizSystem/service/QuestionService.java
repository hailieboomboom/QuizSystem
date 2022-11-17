package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fdmgroup.QuizSystem.exception.McqException.NotEnoughAccessException;
import com.fdmgroup.QuizSystem.model.*;
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
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;




@Service
@Transactional
public class QuestionService {

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
	private UserRepository userRepository;
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private QuizQuestionGradeRepository qqgRepository;

	
	
	public QuestionService(QuestionRepository questionRepository) {
		super();
		this.questionRepository = questionRepository;
	}
	
	public Question save(Question question) {
	        return questionRepository.save(question);
	}

	public List<Question> findAllQuestions(){
		return questionRepository.findAll();
	}
	
	public Question findById(Long id) {
		Optional<Question> opQuestion = questionRepository.findById(id);
		if(opQuestion.isEmpty()) {
			return null;
		}
		return opQuestion.get();
	}
	
	public void remove(Question question) {
		questionRepository.delete(question);
	}
	
	public List<Question> findQuestionsByCreator(User creator){
		return questionRepository.findByCreator(creator);
	}




	@Transactional
	public void createMCQ(AddMcqDto addMcqDto, User user) {



		MultipleChoiceQuestion newQuestion = new MultipleChoiceQuestion();
		newQuestion.setCreator(user);
		newQuestion.setQuestionDetails(addMcqDto.getQuestionDetails());
		newQuestion = (MultipleChoiceQuestion) save(newQuestion);
		try {
			List<String> mcqTag = addMcqDto.getTags();
			mcqTag.add("mcq");
			newQuestion.setTags(tagService.getTagsFromDto(mcqTag));
			newQuestion.setTags(tagService.getTagsFromDto(addMcqDto.getTags()));
			newQuestion.setMcoptions(multipleChoiceOptionService.createListOfOption(addMcqDto.getOptions(), newQuestion));
			newQuestion = (MultipleChoiceQuestion) save(newQuestion);
		} catch (TagNotValidException e) {
			questionRepository.delete(newQuestion);
			throw new TagNotValidException("The question must contain at least a course or interview tag");
		}

	}

	public void accessControlCreateMCQ(List<String> tags, Role role) {
		if(tags==null || tags.isEmpty())
			throw new TagNotValidException("Please provide at least one tag");
		if(role.equals(Role.TRAINING)&& tags.contains("interview")){
			throw new NotEnoughAccessException("Student cannot create interview Questions");
		} else if( role.equals(Role.AUTHORISED_SALES )&& tags.contains("content")){
			 throw new NotEnoughAccessException("Sales cannot create course-related Questions");
		}
	}

 @Transactional
	public void updateMCQ(AddMcqDto addMcqDto, long mcqId) {
		MultipleChoiceQuestion originalMcq = findMcqById(mcqId);
		deleteAttempt(mcqId);
		List<MultipleChoiceOption> updatedOptions = multipleChoiceOptionService.updateMcqOption(addMcqDto.getOptions(), originalMcq);
		System.out.println(updatedOptions);
		originalMcq.setQuestionDetails(addMcqDto.getQuestionDetails());
		originalMcq = (MultipleChoiceQuestion) save(originalMcq);

		Long userId = findCreatorIdOfAQuestion(originalMcq.getId());
		User user = userRepository.findById(userId).get();
		originalMcq.setCreator(user);
		originalMcq.setMcoptions(updatedOptions);
		originalMcq.setTags(tagService.getTagsFromDto(addMcqDto.getTags()));
		this.save(originalMcq);

	}
	
	public void updateMCQByRole(AddMcqDto addMcqDto, long mcqId, long activeUserId) {
		MultipleChoiceQuestion originalMcq = findMcqById(mcqId);
		Set<Tag> originalMCQTags = originalMcq.getTags();
		boolean  isUserValid = false;
		// get user role
		User currentUser = userRepository.findById(activeUserId).get();
		// if current user is a student, he can only update his created question
		if(currentUser instanceof Student) {
			Long creatorId = findCreatorIdOfAQuestion(originalMcq.getId());
			if(!creatorId.equals(activeUserId)) {
				throw new NoDataFoundException("You can't modify this question, because you are not the creator");
			}else {
				updateMCQ(addMcqDto, mcqId);
			}
		}
		// if current user is a trainer, he can only update questions of course content type
		else if(currentUser.getRole().equals(Role.AUTHORISED_TRAINER)) {
			// check question contains interview tag
			for(Tag t:originalMCQTags) {
				if(t.getTagName().equals("course")) {
					isUserValid = true;
				}
			}
			updateMCQ(addMcqDto, mcqId);
//			if(isUserValid) {
//				updateMCQ(addMcqDto, mcqId);
//			}else {
//				throw new NoDataFoundException("You can't modify this question, because trainer can only modify course content questions");
//			}
		}
		// if current user is a sales, he can only update questions of interview content type
		else if(currentUser.getRole().equals(Role.AUTHORISED_SALES)) {
			// check question contains interview tag
			for(Tag t:originalMCQTags) {
				if(t.getTagName().equals("interview")) {
					isUserValid = true;
				}
			}
			if(isUserValid) {
				updateMCQ(addMcqDto, mcqId);
			}else {
				throw new NoDataFoundException("You can't modify this question, because sales can only modify interview questions");
			}
		}
		
	}

	public List<QuestionGradeDTO> getAllMcqQuestionforQuizCreation() {
		var mcqQuestions = mcqRepository.findAll();

		List<QuestionGradeDTO> mcqDtoList = new ArrayList<QuestionGradeDTO>();

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
	 
	public List<QuestionGradeDTO> getMcqDtosforQuizEdit(long quiz_id){
		List<QuestionGradeDTO> dtos = new ArrayList<QuestionGradeDTO>();
		List<MultipleChoiceQuestion> involvedQuestions = new ArrayList<MultipleChoiceQuestion>();
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
	
	public List<ReturnMcqDto> getAllMcqQuestion() {
		var mcqQuestions = mcqRepository.findAll();

		List<ReturnMcqDto> mcqDtoList = new ArrayList<>();

		for (MultipleChoiceQuestion question : mcqQuestions)
			mcqDtoList.add(getReturnMcqDto(question));

		return mcqDtoList;
	}

	public List<ReturnMcqDto> getAllMcqCreatedByAnUser(long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		//Logic
		if (!userOptional.isPresent())
			throw new NoDataFoundException("User Not Found");
		User user = userOptional.get();
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

	private ReturnMcqDto getReturnMcqDto(Question question) {
		ReturnMcqDto returnMcqDto = new ReturnMcqDto();
		returnMcqDto.setQuestionId(question.getId());
		returnMcqDto.setQuestionDetail(question.getQuestionDetails());
		returnMcqDto.setTags(question.getTags().stream().map(tag -> tag.getTagName()).toList());
		returnMcqDto.setMcqOptionDtoList(multipleChoiceOptionService.listOptionsForMcq(question.getId()));
		return returnMcqDto;
	}


	public AddMcqDto getMcqDto(Long questionId) {


		Question mcq = findMcqById(questionId);
		Long userId = findCreatorIdOfAQuestion(questionId);

		List<String> tagList = mcq.getTags().stream().map(tag -> tag.getTagName()).collect(Collectors.toList());
		List<McqOptionDto> optionList = multipleChoiceOptionService.listOptionsForMcq(questionId);
		AddMcqDto addMcqDto = new AddMcqDto();
		addMcqDto.setQuestionDetails(mcq.getQuestionDetails());
		addMcqDto.setUserId(userId);
		addMcqDto.setTags(tagList);
		addMcqDto.setOptions(optionList);

		return addMcqDto;
	}

	public List<ReturnMcqDto> getMcqBank(String type) {
		if(!type.equals("course")&&!type.equals("interview"))
			throw new TagNotValidException("The question bank " + type + " doesn't exist.");
		List<MultipleChoiceQuestion> mcqQuestions = mcqRepository.findAll();
		List<ReturnMcqDto> mcqDtoList = new ArrayList<>();
		ReturnMcqDto returnMcqDto = new ReturnMcqDto();
		for (MultipleChoiceQuestion question : mcqQuestions){
			returnMcqDto = getReturnMcqDto(question);
			if( returnMcqDto.getTags().stream().anyMatch(tag-> tag.equals(type)))
				mcqDtoList.add(returnMcqDto) ;
		}
		return mcqDtoList;
	}



	@Transactional
	public void deleteOneMcq(Long questionId) {
        deleteAttempt(questionId);
		MultipleChoiceQuestion mcq = findMcqById(questionId);
		mcq.setTags(null);
		var returned = questionRepository.save(mcq);
		multipleChoiceOptionService.deleteQuestionOptions(questionId);
		questionRepository.delete(returned);

	}
	
	public void deleteOneMcqByRole(Long questionId, Long activeUserId) {
		MultipleChoiceQuestion originalMcq = findMcqById(questionId);
		Set<Tag> originalMCQTags = originalMcq.getTags();
		boolean  isUserValid = false;
		// get user role
		User currentUser = userRepository.findById(activeUserId).get();
		// if current user is a student, he can only delete his created question
		if(currentUser instanceof Student) {
			Long creatorId = findCreatorIdOfAQuestion(originalMcq.getId());
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
				if(t.getTagName().equals("course")) {
					isUserValid = true;
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
				if(t.getTagName().equals("interview")) {
					isUserValid = true;
				}
			}
			if(isUserValid) {
				deleteOneMcq(questionId);
			}else {
				throw new NoDataFoundException("You can't delete this question, because sales can only delete interview questions");
			}
		}
	}

	
	public MultipleChoiceQuestion findMcqById(Long questionId) {
		if(!isMultipleChoiceQuestion(questionId)){
			throw new NoDataFoundException("Question Not Found");
		}

		Optional<Question> optionalMCQ = questionRepository.findById(questionId);
		if(optionalMCQ.isEmpty()){
			throw new NoDataFoundException("Question Not Found");
		}

		return (MultipleChoiceQuestion) optionalMCQ.get();
	}

	public Long findCreatorIdOfAQuestion(long userId) {
		Optional<Long> optionalId = questionRepository.findCreatorIdOfQuestion(userId);
		if (!optionalId.isPresent())
			throw new NoDataFoundException("You can't modify this question, because we couldn't find the creator of this question");
		return optionalId.get();
	}


	public Boolean isMultipleChoiceQuestion(long questionId) {
		Optional<MultipleChoiceQuestion> mcqOptional = mcqRepository.findById(questionId);
		return mcqOptional.isPresent();
	}

	private void deleteAttempt(Long questionId) {
		var mcqAttemptList = quizQuestionMCQAttemptRepository.findByMcqId(questionId);
		for (Long attempt : mcqAttemptList) {
			var result = quizAttemptService.findQuizAttemptByQuizId(attempt);
			result.forEach(quizAttempt -> quizAttemptService.deleteAttempt(quizAttempt));
		}
	}

   
}
