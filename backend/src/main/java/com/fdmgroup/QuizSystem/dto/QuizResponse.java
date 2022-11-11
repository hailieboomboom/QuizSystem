package com.fdmgroup.QuizSystem.dto;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class QuizResponse {
	
	private long quizId; 
	private long creatorId;
	private String name;
	private QuizCategory quizCategory;

}
