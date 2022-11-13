package com.fdmgroup.QuizSystem.controller;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.repository.QuestionRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizQuestionMCQAttemptService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.dto.QuizRequest;
import com.fdmgroup.QuizSystem.dto.QuizResponse;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import com.fdmgroup.QuizSystem.service.QuizService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuizAttemptController {
	
	private QuizQuestionMCQAttemptService mcqAttemptService;
	
	// create an attempt (return graded attempt)
	
	// view attempts of quizzes created 
	
	// view all attempts by user(student) (user, quiz name/attempt, quiz_grade)
	
	// delete an attempt (associated with a quiz to be deleted)

}
