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
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 *  Service that validates and process quiz related data
 * @author sm
 *
 */
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
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private TagService tagService;

	/**
	 * Creates a quiz to the database and returns the quiz DTO.
	 * @param quizDto The quiz DTO that needs to be stored into database
	 * @return QuizDto The quiz DTO with the quiz id from database
	 */
	public QuizDto createQuiz(QuizDto quizDto) {

		Quiz quizEntity = new Quiz();
		quizEntity.setName(quizDto.getName());
		quizEntity.setQuizCategory(quizDto.getQuizCategory());
		quizEntity.setCreator(userRepository.findById(quizDto.getCreatorId()).get());

		Quiz managedQuiz = quizRepository.save(quizEntity);
		quizDto.setQuizId(managedQuiz.getId());
		return quizDto;
	}

	/**
	 * Persists a Quiz to the database.
 	 * @param quiz
	 * @return
	 */
	public Quiz save(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	/**
	 * Gets quiz DTO from given quiz entity
	 * @param quiz The quiz entity
	 * @return QuizDto that contains selected information of the quiz entity
	 */
	public QuizDto getQuizDto(Quiz quiz) {
		QuizDto quizDto = new QuizDto();
		quizDto.setQuizId(quiz.getId());
		quizDto.setCreatorId(quiz.getCreator().getId());
		quizDto.setName(quiz.getName());
		quizDto.setQuizCategory(quiz.getQuizCategory());
		return quizDto;
	}

	/**
	 * Finds all quizzes
	 * @return List<QuizDto>  A list of quiz DTO
	 */
	public List<QuizDto> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAll();

		List<QuizDto> quizDtos = new ArrayList<>();
		for (Quiz quiz : allQuizzes) {
			quizDtos.add(getQuizDto(quiz));
		}
		return quizDtos;
	}
	
	/**
	 * Updates existing quiz in the database.
	 * @param id The id of the quiz to be updated
	 * @param quizDto The quiz DTO that contains the information to update
	 */
	public void updateQuiz(long id, QuizDto quizDto) {

		// Check if quiz exists
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException("Quiz not found");
		}

		Quiz quiz = optionalQuiz.get();
		quiz.setName(quizDto.getName());
		quiz.setQuizCategory(quizDto.getQuizCategory());
		quiz.setCreator(userRepository.findById(quizDto.getCreatorId()).get());

		quizRepository.save(quiz);
	}

	/**
	 * Delete quiz by quiz id.
	 * @param id The id of the quiz to be deleted.
	 */
	public void deleteQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException("Quiz not found");
		}

		quizRepository.deleteById(id); // related QuizQuestionGrade list will be removed behind the scene for "cascade
										// = CascadeType.REMOVE"

	}

	/**
	 * Finds quiz by quiz id
	 * @param id The id of the quiz to be returned
	 * @return Quiz the managed quiz in the database
	 */
	public Quiz getQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException("Quiz not found");
		}
		return optionalQuiz.get();
	}

	/**
	 * Finds all quizzes by quiz category.
	 * @param quizCategory The quiz category to be found
	 * @return List<Quiz> A list of quiz entity
	 */
	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);

	}

	
	/**
	 * Adds a question with a corresponding grade into the quiz.
	 * @param question The question to be added into the quiz
	 * @param quiz The quiz to be added to
	 * @param grade The grade to be added to the question 
	 */
	public void addQuestionIntoQuiz(Question question, Quiz quiz, Float grade) {

		question = questionRepo.findById(question.getId()).get();
		quiz = quizRepository.findById(quiz.getId()).get();
		
		QuizQuestionGradeKey qqgkey = new QuizQuestionGradeKey(quiz.getId(), question.getId());
		QuizQuestionGrade qqg = new QuizQuestionGrade();
		qqg.setKey(qqgkey);
		qqg.setGrade(grade);
		qqg.setQuestion(question);
		qqg.setQuiz(quiz);

		qqgService.save(qqg);
	}

	/**
	 * Removes a question from the quiz.
	 * @param question The question to be removed
	 * @param quiz The quiz to be removed from
	 */
	public void removeQuestionFromQuiz(Question question, Quiz quiz) {
		
		QuizQuestionGradeKey qqgkey = new QuizQuestionGradeKey(quiz.getId(), question.getId());
		QuizQuestionGrade quizQuestion = qqgService.findById(qqgkey);
		
		if (quizQuestion != null) {
			qqgService.remove(quizQuestion);
		}
	}

	/**
	 * Find all quizzes by quiz creator id.
	 * @param creatorId The id of the quiz creator
	 * @return List<QuizDto> A list of quiz DTO
	 */
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

	/**
	 * Finds maximum grade of a quiz.
	 * @param quizId The id of the quiz
	 * @return float The maximum grade of a quiz 
	 */
	public float getMaxGrade(Long quizId) {
		Quiz quiz = getQuizById(quizId);
		float maxGrade = 0;
		for (QuizQuestionGrade qqg : quiz.getQuizQuestionsGrade()) {
			maxGrade += qqg.getGrade();
		}
		return maxGrade;
	}


	/**
	 * Checks if the active user(currently logged-in user has the authority to access a given quiz category.
	 * @param requestQuizCategory The quiz category to be accessed
	 * @param activeUserId The id of the active user
	 */
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


	/**
	 * Checks if the active user (currently logged-in user) has authority to access the quiz.
	 * @param quizId The id of the quiz to be accessed
	 * @param activeUserId The id of the active user
	 */
	public void checkAccessToQuizId(long quizId, long activeUserId) {

		long quizCreatorId = getQuizById(quizId).getCreator().getId();
		Role activeUserRole = userService.getUserById(activeUserId).getRole();

		if (quizCreatorId != activeUserId && activeUserRole != Role.AUTHORISED_TRAINER
				&& activeUserRole != Role.AUTHORISED_SALES) {
			throw new UserUnauthorisedError(
					"You do not have access to update or delete quizzes that is created by other users!");
		}
	}

	/**
	 * Check if the tag of the question matches with the given quiz category.
	 * @param quizCategory The quiz category
	 * @param question The question
	 */
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
		
	}

	/**
	 * Creates questions of the quiz. 
	 * @param quiz_id The id of the quiz to add questions to
	 * @param questionGradeDtoList The list of QuestionGradeDTO
	 */
	public void createQuizQuestions(long quiz_id, List<QuestionGradeDTO> questionGradeDtoList) {

		Quiz quiz = getQuizById(quiz_id);
		QuizCategory quizCategory = quiz.getQuizCategory();

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

	/**
	 * Updates the questions of the quiz
	 * @param quiz_id The id of the quiz to add questions to
	 * @param questionGradeDtoList The list of QuestionGradeDTO
	 */
	public void updateQuizQuestions(long quiz_id, List<QuestionGradeDTO> questionGradeDtoList) {

		Quiz quiz = getQuizById(quiz_id);
		QuizCategory quizCategory = quiz.getQuizCategory();

		List<QuizQuestionGrade> quizQuestionGradeList = quizQuestionGradeService.findAllByQuizId(quiz_id);

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
