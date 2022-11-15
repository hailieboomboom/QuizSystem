package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdmgroup.QuizSystem.dto.McqDto.AddMcqDto;
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.ReturnMcqDto;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.TagNotValidException;
import com.fdmgroup.QuizSystem.model.*;
import com.fdmgroup.QuizSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionService {
	@Autowired
	QuizQuestionMCQAttemptRepository quizQuestionMCQAttemptRepository;
	@Autowired
    private QuestionRepository questionRepository;
	@Autowired
	QuizAttemptService quizAttemptService;
	@Autowired
	private McqRepository mcqRepository;
	@Autowired
	private TagService tagService;
	@Autowired
	private MultipleChoiceOptionService multipleChoiceOptionService;
	@Autowired
	private UserRepository userRepository;
	
	
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



	public void createMCQ(AddMcqDto addMcqDto) {


		Optional<User> userOptional = userRepository.findById(addMcqDto.getUserId());
		if (!userOptional.isPresent())
			throw new NoDataFoundException("Error, user doesn't exists");

		MultipleChoiceQuestion newQuestion = new MultipleChoiceQuestion();
		newQuestion.setCreator(userOptional.get());
		newQuestion.setQuestionDetails(addMcqDto.getQuestionDetails());
		newQuestion = (MultipleChoiceQuestion) save(newQuestion);
		newQuestion.
				setTags(tagService.getTagsFromDto(addMcqDto.getTags()));
		newQuestion.
				setMcoptions(multipleChoiceOptionService.createListOfOption(addMcqDto.getOptions(), newQuestion)
				);
		this.save(newQuestion);

	}


	public void updateMCQ(AddMcqDto addMcqDto, long mcqId) {
		MultipleChoiceQuestion originalMcq = findMcqById(mcqId);

		List<MultipleChoiceOption> updatedOptions = multipleChoiceOptionService.updateMcqOption(addMcqDto.getOptions(), originalMcq.getId());
		originalMcq.setQuestionDetails(addMcqDto.getQuestionDetails());
		originalMcq = (MultipleChoiceQuestion) save(originalMcq);

		Long userId = findCreatorIdOfAQuestion(originalMcq.getId());
		User user = userRepository.findById(userId).get();

		originalMcq.setCreator(user);
		originalMcq.setMcoptions(updatedOptions);
		originalMcq.setTags(tagService.getTagsFromDto(addMcqDto.getTags()));
		this.save(originalMcq);
//		System.out.println("Hi there" + originalMcq.getId());
		deleteAttempt(originalMcq.getId());
	}

	private void deleteAttempt(Long questionId) {
		var mcqAttemptList= quizQuestionMCQAttemptRepository.findByMcqId(questionId);
		for (Long attempt : mcqAttemptList){
			var result = quizAttemptService.findQuizAttemptByQuizId(attempt);
			result.forEach(quizAttempt -> quizAttemptService.deleteAttempt(quizAttempt));
		}
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
		returnMcqDto.setQuestionDetail(question.getQuestionDetails());
		returnMcqDto.setTags(question.getTags().stream().map(tag -> tag.getTagName()).toList());
		returnMcqDto.setMcqOptionDtoList(multipleChoiceOptionService.listOptionsForMcq(question.getId()));
		return returnMcqDto;
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


	@Transactional
	public void deleteOneMcq(Long questionId) {
        deleteAttempt(questionId);
		MultipleChoiceQuestion mcq = findMcqById(questionId);
		mcq.setTags(null);
		var returned = questionRepository.save(mcq);
		multipleChoiceOptionService.deleteQuestionOptions(questionId);
		questionRepository.delete(returned);


	}

	//
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



//    public Question getQuestionById(long id){
//
//        Optional<Question> maybeQuestion = questionRepository.findById(id);
//        if(maybeQuestion.isEmpty()){
//            throw new QuestionNotFoundException();
//        }
//        return maybeQuestion.get();
//    }
//
//
//    public Question getQuestionByQuestionname(String questionname){
//        Optional<Question> maybe_question = questionRepository.findQuestionByQuestionname(questionname);
//        if(maybe_question.isEmpty()){
//            throw new QuestionNotFoundException();
//        }
//        return maybe_question.get();
//    }
//
//    
//    public void deleteQuestionById(long id){
//        if(questionRepository.existsById(id)){
//            questionRepository.deleteById(id);
//        }
//        else {
//            throw new QuestionNotFoundException();
//        }
//
//    }
//
//    
//    public Question updateQuestion(long id, Question modifiedQuestion) {
//        Optional<Question> maybeQuestion = questionRepository.findById(id);
//        if(maybeQuestion.isEmpty()){
//            throw new QuestionNotFoundException();
//        }
//        // Update question with new attributes
//        Question question = maybeQuestion.get();
////        question.setAvatar(input.getAvatar());
//        question.setQuestionname(modifiedQuestion.getQuestionname());
//        question.setPassword(modifiedQuestion.getPassword());
//        question.setEmail(modifiedQuestion.getEmail());
//        return questionRepository.save(question);
//    }
//
//    
//    public boolean existsByQuestionname(String questionname){
//        return questionRepository.existsByQuestionname(questionname);
//    }
//
//    
//    public boolean existsByEmail(String email){
//        return questionRepository.existsByEmail(email);
//    }
//
//   
   
}
