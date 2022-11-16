package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.exception.QuestionTagNotMatchQuizCategory;
import com.fdmgroup.QuizSystem.exception.QuizAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuizQuestionGradeService qqgService;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuizQuestionGradeService quizQuestionGradeService;

	@Autowired
	private QuizQuestionGradeRepository qqgRepository;

	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private TagService tagService;

	public QuizDto createQuiz(QuizDto quizDto) {

		Quiz quizEntity = new Quiz();
		quizEntity.setName(quizDto.getName());
		quizEntity.setQuizCategory(quizDto.getQuizCategory());
//		quizEntity.setQuestions(quizDto.getQuestions());
		quizEntity.setCreator(userRepository.findById(quizDto.getCreatorId()).get());

		Quiz managedQuiz = quizRepository.save(quizEntity);
		quizDto.setQuizId(managedQuiz.getId());
		return quizDto;
	}

	public Quiz save(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	public QuizDto getQuizDto(Quiz quiz) {
		QuizDto quizDto = new QuizDto();
		quizDto.setQuizId(quiz.getId());
		quizDto.setCreatorId(quiz.getCreator().getId());
		quizDto.setName(quiz.getName());
		quizDto.setQuizCategory(quiz.getQuizCategory());
//		quizDto.setQuestions(quiz.getQuestions());
		return quizDto;
	}

	public List<QuizDto> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAll();

		List<QuizDto> quizDtos = new ArrayList<>();
		for (Quiz quiz : allQuizzes) {
			quizDtos.add(getQuizDto(quiz));
		}
		return quizDtos;
	}

	// update quiz details (not including questions)
	public void updateQuiz(long id, QuizDto quizDto) {

		// Check if quiz exists
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException("Quiz not found");
		}

		Quiz quiz = optionalQuiz.get();
		quiz.setName(quizDto.getName());
		quiz.setQuizCategory(quizDto.getQuizCategory());
//		quiz.setQuestions(quizDto.getQuestions());
		quiz.setCreator(userRepository.findById(quizDto.getCreatorId()).get());

		quizRepository.save(quiz);
	}

	public void deleteQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException("Quiz not found");
		}

//		// TODO This part is handled by CascadeType.REMOVE on Quiz.java
//		// To be deleted by Summer: find all of the quizQuestionGrade associated with the quiz, loop and remove all of them.
//		List<QuizQuestionGrade> qqgsToRemove = qqgRepository.findByQuizId(id);
//		for(QuizQuestionGrade qqg: qqgsToRemove) {
//			qqgRepository.delete(qqg);
//		}

		quizRepository.deleteById(id); // related QuizQuestionGrade list will be removed behind the scene for "cascade
										// = CascadeType.REMOVE"

	}

	public Quiz getQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException("Quiz not found");
		}
		return optionalQuiz.get();
	}

	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);

	}

	// TODO: parameters can be just Ids instead of entities? (from Yutta and Jason)
	public void addQuestionIntoQuiz(Question question, Quiz quiz, Float grade) {

		System.out.println("----ENTER ADDQUESTION: question id is " + question.getId() + " quiz id is " + quiz.getId());

		question = questionRepo.findById(question.getId()).get();
		quiz = quizRepository.findById(quiz.getId()).get();
		QuizQuestionGradeKey qqgkey = new QuizQuestionGradeKey(quiz.getId(), question.getId());
		QuizQuestionGrade quizQuestion = new QuizQuestionGrade();
		quizQuestion.setKey(qqgkey);
		quizQuestion.setGrade(grade);
		quizQuestion.setQuestion(question);
		quizQuestion.setQuiz(quiz);
		System.out.println("after setkey try to get key: " + quizQuestion.getKey().getQuestionId() + "  "
				+ quizQuestion.getKey().getQuizId());
		qqgService.save(quizQuestion);
	}

	public void removeQuestionFromQuiz(Question question, Quiz quiz) {
		QuizQuestionGradeKey qqgkey = new QuizQuestionGradeKey(quiz.getId(), question.getId());
		QuizQuestionGrade quizQuestion = qqgService.findById(qqgkey);
		if (quizQuestion != null) {
			qqgService.remove(quizQuestion);
		}
	}

	public List<QuizDto> getQuizzesByCreatorId(long creatorId) {

		Optional<User> optionalCreator = userRepository.findById(creatorId);

		if (optionalCreator.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}

		List<Quiz> quizzes = quizRepository.findByCreator(optionalCreator.get());

		List<QuizDto> quizDtos = new ArrayList<>();
		for (Quiz quiz : quizzes) {
			quizDtos.add(getQuizDto(quiz));
		}
		return quizDtos;
	}

	public float getMaxGrade(Long quizId) {
		Quiz quiz = getQuizById(quizId);
		float maxGrade = 0;
		for (QuizQuestionGrade qqg : quiz.getQuizQuestionsGrade()) {
			maxGrade += qqg.getGrade();
		}
		return maxGrade;
	}

//  // TODO for Summer: to be deleted once confirmed everything works
//	public void checkAccessToQuizCategory(QuizCategory requestQuizCategory, long activeUserId) {
//
//		Role activeUserRole = userService.getUserById(activeUserId).getRole();
//
//		// Sales only have access to interview quizzes, but not course quizzes
//		if (requestQuizCategory == QuizCategory.COURSE_QUIZ && activeUserRole == Role.AUTHORISED_SALES) {
//			throw new UserUnauthorisedError("You do not have access to create, update or delete course quizzes!");
//		}
//		// Training students only have access to course quizzes, but not interview
//		// quizzes
//		if (requestQuizCategory == QuizCategory.INTERVIEW_QUIZ && activeUserRole == Role.TRAINING) {
//			throw new UserUnauthorisedError("You do not have access to create, update or delete interview quizzes!");
//		}
//	}
	public void checkAccessToQuizCategory(QuizCategory requestQuizCategory, long activeUserId) {

		HashMap<QuizCategory, Set<Role>> quizRoleMap = new HashMap<>();
		quizRoleMap.put(QuizCategory.COURSE_QUIZ, new HashSet<Role>(Arrays.asList(
				Role.AUTHORISED_TRAINER,
				Role.POND,Role.BEACHED,
				Role.TRAINING)));
		quizRoleMap.put(QuizCategory.INTERVIEW_QUIZ, new HashSet<Role>(Arrays.asList(
				Role.AUTHORISED_TRAINER,
				Role.POND,Role.BEACHED,
				Role.AUTHORISED_SALES)));
		
		Role activeUserRole = userService.getUserById(activeUserId).getRole();
		Set<Role> authorisedRoleSet = quizRoleMap.get(requestQuizCategory);
		
		if(!authorisedRoleSet.contains(activeUserRole)) {
			throw new UserUnauthorisedError("You do not have access to create, update or delete " + requestQuizCategory + " quizzes!");
		}
	}

	// Only the quiz creator edit its own quiz, or trainer/sales can edit others'
	// quiz
	public void checkAccessToQuizId(long quizId, long activeUserId) {

		long quizCreatorId = getQuizById(quizId).getCreator().getId();
		Role activeUserRole = userService.getUserById(activeUserId).getRole();

		if (quizCreatorId != activeUserId && activeUserRole != Role.AUTHORISED_TRAINER
				&& activeUserRole != Role.AUTHORISED_SALES) {
			throw new UserUnauthorisedError(
					"You do not have access to update or delete quizzes that is created by other users!");
		}
	}

	public void checkQuestionTagMatchQuizCategory(QuizCategory quizCategory, Question question) {

		HashMap<QuizCategory, String> quizQuestionMap = new HashMap<>(
				Map.of(QuizCategory.COURSE_QUIZ, "course", QuizCategory.INTERVIEW_QUIZ, "interview"));
		
		Set<Tag> questionTags = question.getTags();
		System.out.println("questionTags: " + questionTags);
		String requiredTagName = quizQuestionMap.get(quizCategory);
		System.out.println("requiredTagName: " + requiredTagName);
		Tag requiredTag = tagService.getTagByName(requiredTagName);
		
		if (!questionTags.contains(requiredTag)) {
			throw new QuestionTagNotMatchQuizCategory("Question " + question.getQuestionDetails()
					+ "' does not match with quiz category " + quizCategory);
		}

//		 // If quiz category is "COURSE", only the questions that are tagged with
//		 // "course" can be added into quiz
//  
//		Set<Tag> questionTags = question.getTags();
//		Tag requiredTag = tagService.getTagByName("course");
//		
//		if (!questionTags.contains(requiredTag)) {
//			throw new QuestionTagNotMatchQuizCategory("Question " + question.getQuestionDetails()
//					+ "can't be added as it does not have a tag that matches " + quizCategory);
//		}
		
	}

	public void createQuizQuestions(long quiz_id, List<QuestionGradeDTO> questionGradeDtoList) {

		Quiz quiz = getQuizById(quiz_id);
		QuizCategory quizCategory = quiz.getQuizCategory();

		// TODO for Summer: to confirm if this step is needed
		// check if the quizQuestionGrade record already exists for the given quiz id
		List<QuizQuestionGrade> foundQuizQuestionGrades = quizQuestionGradeService.findAllByQuizId(quiz_id);
		if(foundQuizQuestionGrades.size() != 0) {
			throw new QuizAlreadyExistsException("This quiz already already contains questions and grades");
		}

		for (QuestionGradeDTO questionGradeDTO : questionGradeDtoList) {
			
			Question question = questionService.findById(questionGradeDTO.getQuestionId());
			float grade = questionGradeDTO.getGrade();

			// Check if the question tag match with quiz category
			checkQuestionTagMatchQuizCategory(quizCategory, question);

			addQuestionIntoQuiz(question, quiz, grade);
		}
	}

	public void updateQuizQuestions(long quiz_id, List<QuestionGradeDTO> questionGradeDtoList) {

		Quiz quiz = getQuizById(quiz_id);
		QuizCategory quizCategory = quiz.getQuizCategory();

		List<QuizQuestionGrade> quizQuestionGradeList = quizQuestionGradeService.findAllByQuizId(quiz_id);
		// Database
		Set<Long> questionIdSet = quizQuestionGradeList.stream()
				.map(quizQuestionGrade -> quizQuestionGrade.getQuestion().getId()).collect(Collectors.toSet());
		Set<Long> questionIdInputSet = questionGradeDtoList.stream().map(QuestionGradeDTO::getQuestionId)
				.collect(Collectors.toSet());

		// if user adds new questions
		for (QuestionGradeDTO questionGradeDTO : questionGradeDtoList) {
			if (!questionIdSet.contains(questionGradeDTO.getQuestionId())) {
				Question question = questionService.findById(questionGradeDTO.getQuestionId());
				float grade = questionGradeDTO.getGrade();
				
				// Check if the question tag match with quiz category
				checkQuestionTagMatchQuizCategory(quizCategory, question);
				
				addQuestionIntoQuiz(question, quiz, grade);
			} else if (questionGradeDTO.getGrade() != (quizQuestionGradeService
					.findById(new QuizQuestionGradeKey(quiz_id, questionGradeDTO.getQuestionId())).getGrade())) {
				QuizQuestionGrade quizQuestionGrade = quizQuestionGradeService
						.findById(new QuizQuestionGradeKey(quiz_id, questionGradeDTO.getQuestionId()));
				quizQuestionGrade.setGrade(questionGradeDTO.getGrade());
				quizQuestionGradeService.save(quizQuestionGrade);
			}
		}
		// if user remove existing questions
		for (Long questionId : questionIdSet) {
			if (!questionIdInputSet.contains(questionId)) {
				Question question = questionService.findById(questionId);
				removeQuestionFromQuiz(question, quiz);
			}
		}
	}

}
