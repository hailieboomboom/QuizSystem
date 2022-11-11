package com.fdmgroup.QuizSystem.dto;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class QuizRequest {
	
	private long quizId; 
	private long creatorId;
	private String name;
	private QuizCategory quizCategory;
	
	
	// HOW should I add questionDto here into quizDto
	// should I make the 2 question type into 2 seperate fields like this?
//	private List<MCQDto> mcqDtos;
//	private List<SAQDto> saqDtos;
//	private List<Question> questions;

}
